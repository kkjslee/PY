package com.inforstack.openstack.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.KeystoneService.Role;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
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

	@Override
	public Set<Permission> getPermissions(Integer userId) {
		Set<Permission> permissions = new HashSet<Permission>();
		log.debug("Get permissions by user id : " + userId);
		User user = userDao.findById(userId);
		if (user == null) {
			log.info("Get permisstions failed for no user found by id : "
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
			log.info("No user found by name : " + userName);
		} else {
			log.debug("Find user successfully");
		}

		return user;
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

		UserService self = (UserService) OpenstackUtil.getBean("userService");
		fillUser(user, t);
		User u = self.createUser(user, roleId);
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
		user.setStatus(Constants.USER_STATUS_VALID);
		user.setAgeing(Constants.USER_AGEING_ACTIVE);
		user.setCreateTime(new Date());
		user.setPassword(OpenstackUtil.md5(user.getPassword()));
		userDao.persist(user);

		user.setOpenstackUser(keystoneService.createUser(user.getUsername(),
				user.getPassword(), user.getEmail()));
		user.setUuid(user.getOpenstackUser().getId());
		log.debug("create user successfully");
		return user;
	}

	@Override
	public User updateUser(User user) throws OpenstackAPIException {
		User newUser = userDao.findById(user.getId());
		if (newUser == null) {
			log.warn("Update user failed for no user found for id :　"
					+ user.getId());
			return null;
		}

		boolean mergeWithOpenstack = false;
		com.inforstack.openstack.api.keystone.User ou1 = user
				.getOpenstackUser();
		com.inforstack.openstack.api.keystone.User ou2 = newUser
				.getOpenstackUser();
		if (!ou1.getEmail().equals(ou2.getEmail())
				|| !ou1.getName().equals(ou2.getName())
				|| !ou1.getPassword().equals(ou2.getPassword())
				|| !ou1.isEnabled() == ou2.isEnabled()) {
			mergeWithOpenstack = true;
		}

		log.debug("Update user : " + user.getUsername());
		newUser = userDao.findById(user);
		if (newUser == null) {
			log.warn("Update user failed");
			return null;
		}

		if (mergeWithOpenstack) {
			keystoneService.updateUser(ou1);
		}
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
		UserService self = (UserService) OpenstackUtil.getBean("userService");
		User u = self.createUser(user, t.getRoleId());
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
			log.info("No user instance found for user id : " + userId);
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
			log.info("Check question failed for no user find by user name : "
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
		List<User> users = userDao.list();

		if (CollectionUtil.isNullOrEmpty(users)) {
			log.debug("No user found");
			return new ArrayList<User>();
		} else {
			log.debug(users.size() + " users found");
			return users;
		}
	}

}
