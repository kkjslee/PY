package com.inforstack.openstack.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.h2.util.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.KeystoneService.Role;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.mail.MailService;
import com.inforstack.openstack.mail.task.code.TaskCode;
import com.inforstack.openstack.mail.task.code.TaskCodeService;
import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.CryptoUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	private static final Logger log = new Logger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private KeystoneService keystoneService;
	@Autowired
	private MailService mailService;
	@Autowired
	private TaskCodeService taskCodeService;

	@Override
	public Set<Permission> getPermissions(Integer userId) {
		Set<Permission> permissions = new HashSet<Permission>();
		log.debug("Get permissions by user id : " + userId);
		User user = userDao.findById(userId);
		if (user == null) {
			log.debug("Get permisstions failed for no user found by id : "
					+ userId);
			return permissions;
		}

		List<SecurityGroup> sgs = user.getSecurityGroups();
		for (SecurityGroup sg : sgs) {
			permissions.addAll(sg.getPermissions());
		}
		log.debug(permissions.size() + " permissions found for user : "
				+ userId);

		return permissions;
	}

	@Override
	public User findUserById(int userId) {
		log.debug("Find user by id : " + userId);
		User user = userDao.findById(userId);
		if (user == null) {
			log.debug("Find user failed for no instance found");
		} else {
			log.debug("Find user successfully");
		}

		return user;
	}

	@Override
	public User findByName(String userName) {
		log.debug("find user by name : " + userName);
		User user = userDao.findByName(userName);
		if (user == null) {
			log.debug("No user found by name : " + userName);
		} else {
			log.debug("Find user successfully");
		}

		return user;
	}
	
	@Override
	public String getOpenstackUserPassword(){
		return OpenstackUtil.getConfig(Constants.CONFIG_OPENSTACK_USER_PASSWORD);
	}

	@Override
	public User registerUser(User user, Tenant tenant, int roleId)
			throws OpenstackAPIException {
		log.debug("Register user with user : " + user.getUsername()
				+ ", tenant : " + tenant.getName());
		tenant.setRoleId(roleId);
		Tenant t = tenantService.createTenant(tenant, roleId);
		if (t == null) {
			log.warn("Register user failed for creating tenant failed : "
					+ tenant.getName());
			throw new ApplicationRuntimeException(
					OpenstackUtil.getMessage("tenant.create.fail"));
		}

		fillUser(user, t);
		User u = this.createUser(user, roleId);
		if (u == null) {
			log.warn("Register user failed for creating user failed : "
					+ user.getUsername());
			throw new ApplicationRuntimeException(
					OpenstackUtil.getMessage("user.create.failed"));
		}

		t.setCreator(user);
		keystoneService.addRole(OpenstackUtil.getOpenstackRole(roleId),
				u.getOpenstackUser(), t.getOpenstatckTenant());
		log.debug("Register user successfully");

		return user;
	}

	@Override
	public User createUser(User user, int roleId) throws OpenstackAPIException {
		log.debug("Create user : " + user.getUsername());
		user.setRoleId(roleId);
		user.setStatus(Constants.USER_STATUS_INVALID);
		user.setAgeing(Constants.USER_AGEING_ACTIVE);
		user.setCreateTime(new Date());
		user.setPassword(CryptoUtil.md5(user.getPassword()));
		userDao.persist(user);

		user.setOpenstackUser(keystoneService.createUser(user.getUsername(),
				this.getOpenstackUserPassword(), ""));
		user.setUuid(user.getOpenstackUser().getId());
		log.debug("create user successfully");
		return user;
	}

	@Override
	public User updateUser(User user) {
		User newUser = userDao.findById(user.getId());
		if (newUser == null) {
			log.warn("Update user failed for no user found for id :　"
					+ user.getId());
			return null;
		}

		log.debug("Update user : " + user.getUsername());
		userDao.update(user);

		log.debug("Update user successfully");
		return newUser;
	}

	@Override
	public User createTenantUser(User user) throws OpenstackAPIException {
		Tenant t = SecurityUtils.getTenant();
		if (t == null) {
			log.error("Create tenant user failed for tenant is not found");
			throw new ApplicationRuntimeException(
					OpenstackUtil.getMessage("tenant.not.found"));
		}

		log.debug("Create user for tenant : " + t.getName());
		fillUser(user, t);
		User u = this.createUser(user, t.getRoleId());
		if (u == null) {
			log.warn("Create tenant user failed");
			throw new ApplicationRuntimeException(
					OpenstackUtil.getMessage("tenant.create.fail"));
		}

		keystoneService.addRole(Role.MEMBER, u.getOpenstackUser(),
				t.getOpenstatckTenant());
		log.debug("create user successfully for tenant : " + t.getName());
		return u;
	}

	private User fillUser(User user, Tenant t) {
		user.setRoleId(t.getRoleId());
		user.setDefaultTenant(t);
		user.getTanents().clear();
		user.getTanents().add(t);

		return user;
	}

	@Override
	public User deleteUser(int userId) throws OpenstackAPIException {
		log.debug("Delete User");
		Tenant tenant = SecurityUtils.getTenant();
		if (tenant.getCreatorId() == userId) {
			throw new ApplicationRuntimeException(
					OpenstackUtil.getMessage("tenant.creator.cannot.delete"));
		}

		User user = userDao.findById(userId);
		if (user == null) {
			log.warn("No user instance found for user id : " + userId);
			return null;
		}
		userDao.remove(user);

		return user;
	}

	@Override
	public boolean checkQuestion(String username, String question, String answer) {
		log.debug("Check Question with user name : " + username);
		User user = userDao.findByName(username);
		if (user == null) {
			log.warn("Check question failed for no user find by user name : "
					+ username);
			return false;
		}

		if (question.equals(user.getQuestion()) && answer.equals(answer)) {
			log.debug("Check successfully");
			return true;
		}

		log.debug("Check failed");
		return false;
	}

	@Override
	public PaginationModel<User> pagination(int pageIndex, int pageSize) {
		return userDao.pagination(pageIndex, pageSize);
	}

	@Override
	public List<User> listAll() {
		log.debug("List all users");
		List<User> users = userDao.listAll();

		if (CollectionUtil.isNullOrEmpty(users)) {
			log.debug("No user found");
			return new ArrayList<User>();
		} else {
			log.debug(users.size() + " users found");
			return users;
		}
	}

	@Override
	public User findByNameAndEmail(String userName, String email) {
		log.debug("getting User instance with name: " + userName
				+ "and email: " + email);
		User user = userDao.findByNameAndEmail(userName, email);
		if (user == null) {
			log.debug("getting User instance with name: " + userName
					+ "and email: " + email);
		} else {
			log.debug("Find user successfully");
		}

		return user;
	}

	@Override
	public void sendActiveUserEmail(User user, String url) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Constants.MAILTEMPLATE_PROPERTY_USER, user);
		TaskCode tc = taskCodeService.createTaskCode(Constants.MAIL_CODE_VALIDATE_USER, user.getId()+"", null);
		properties.put(Constants.MAILTEMPLATE_PROPERTY_TASKCODE, tc);
		properties.put(Constants.MAILTEMPLATE_PROPERTY_URL, url+"?random="+tc.getRandom());
		mailService.addMailTask(Constants.MAIL_CODE_VALIDATE_USER,
				user.getEmail(), user.getDefaultLanguage(), properties, Constants.MAILTASK_PRIORITY_HIGH);
	}

	@Override
	public User active(String mailCode, String random) {
		TaskCode tc = taskCodeService.findTaskCode(mailCode, random);
		if(tc == null){
			return null;
		}
		
		Integer userId = NumberUtil.getInteger(tc.getEntityId());
		if(userId == null){
			return null;
		}
		
		User user = this.findUserById(userId);
		user.setStatus(Constants.USER_STATUS_VALID);
		taskCodeService.removeTaskCode(tc);
		
		return user;
	}

	@Override
	public void sendResetPasswordEmail(User user, String url) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Constants.MAILTEMPLATE_PROPERTY_USER, user);
		TaskCode tc = taskCodeService.createTaskCode(Constants.MAIL_CODE_RESET_PASSWORD, user.getId()+"", null);
		properties.put(Constants.MAILTEMPLATE_PROPERTY_TASKCODE, tc);
		properties.put(Constants.MAILTEMPLATE_PROPERTY_URL, url+"?random="+tc.getRandom());
		mailService.addMailTask(Constants.MAIL_CODE_RESET_PASSWORD,
				user.getEmail(), user.getDefaultLanguage(), properties, Constants.MAILTASK_PRIORITY_HIGH);
	}

}
