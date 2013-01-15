package com.inforstack.openstack.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.keystone.KeystoneService.Role;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Service("UserService")
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {
	
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private KeystoneService keystoneService;
	
	@Override
	public Set<Permission> getPermissions(Integer userId) {
		Set<Permission> permissions = new HashSet<Permission>();
		if(userId==null){
			log.info("Get permissions failed for passed user id is null");
			return permissions;
		}
		
		log.debug("get permission by user id : " +userId);
		User user = userDao.findUser(userId);
		if(user == null) {
			log.info("No user found for id : " +userId);
			return permissions;
		}
		
		List<SecurityGroup> sgs = user.getSecurityGroups();
		for(SecurityGroup sg : sgs){
			permissions.addAll(sg.getPermissions());
		}
		log.debug(permissions.size()+ " permissions found for user id : " +userId);
		
		return permissions;
	}

	@Override
	public User findByName(String userName) {
		if(userName == null){
			log.info("Find user by name failed for passed user name is null");
			return null;
		}
		
		log.debug("find user by name : " +userName);
		User user = userDao.findByName(userName);
		if(user==null){
			log.info("No user found by name : " + userName);
		}else{
			log.debug("Find user successfully");
		}
		
		return user;
	}

	@Override
	public void registerUser(User user, Tenant tenant) throws OpenstackAPIException, ApplicationException  {
		if(user==null || tenant==null){
			log.info("Register user failed for passed user/tenant is null");
			return;
		}
		
		log.debug("register user : " + user.getName() +", tenant : " + tenant.getName());
		tenant.setRoleId(Constants.ROLE_USER);
		Tenant t = tenantService.createTenant(tenant);
		if(t == null){
			log.warn("create tenant failed" + tenant.getName());
			throw new ApplicationException("tenant.create.fail");
		}
		
		UserService self = (UserService)OpenstackUtil.getBean("UserService");
		fillUser(user, t);
		User u = self.createUser(user);
		if(u == null){
			log.warn("create user failed" + user.getName());
			throw new ApplicationException("user.create.fail");
		}
		
		t.setCreator(user);
		keystoneService.addRole(Role.MEMBER, u.getOpenstackUser(), t.getOpenstatckTenant());
	}

	@Override
	public User createUser(User user) throws OpenstackAPIException {
		if(user==null) {
			log.info("Create user failed for null is passed");
			return null;
		}
		
		log.debug("Create user : "+user.getName());
		user.setStatus(Constants.USER_STATUS_VALID);
		user.setAgeing(Constants.USER_AGEING_ACTIVE);
		user.setCreateTime(new Date());
		userDao.persist(user);
		
		user.setOpenstackUser(keystoneService.createUser(user.getName(), user.getPassword(), user.getEmail()));
		user.setUuid(user.getOpenstackUser().getId());
		log.debug("create user successfully");
		return user;
	}

	@Override
	public User updateUser(User user) throws OpenstackAPIException {
		if(user==null || user.getId()==null) {
			log.info("Update user failed for null is passed or no id found");
			return null;
		}
		
		User newUser = userDao.findUser(user.getId());
		if(newUser==null){
			log.warn("Update user failed for no user found for id :　" + user.getId());
			return null;
		}
		
		boolean mergeWithOpenstack = false;
		com.inforstack.openstack.api.keystone.User ou1 = user.getOpenstackUser();
		com.inforstack.openstack.api.keystone.User ou2 = newUser.getOpenstackUser();
		if (!ou1.getEmail().equals(ou2.getEmail())
				|| !ou1.getName().equals(ou2.getName())
				|| !ou1.getPassword().equals(ou2.getPassword())
				|| !ou1.isEnabled()==ou2.isEnabled()){
			mergeWithOpenstack = true;
		}

		log.debug("Update user : "+user.getName());
		newUser = userDao.merge(user);
		if(newUser == null){
			log.warn("Update user failed");
			return null;
		}
		
		if(mergeWithOpenstack){
			keystoneService.updateUser(ou1);
		}
		log.debug("Update user successfully");
		return newUser;
	}

	@Override
	public User createTenantUser(User user) throws OpenstackAPIException, ApplicationException {
		if(user==null) {
			log.info("Create tenant user failed for null is passed");
			return null;
		}
		Tenant t = SecurityUtils.getTenant();
		if(t == null){
			log.error("Create tenant user failed for tenant is not found");
			throw new ApplicationException(OpenstackUtil.getMessage("tenant.not.found"));
		}
		
		log.debug("Create user for tenant : "  + t.getName());
		fillUser(user, t);
		UserService self = (UserService)OpenstackUtil.getBean("UserService");
		User u = self.createUser(user);
		if(u == null){
			log.warn("Create tenant user failed");
			return null;
		}
		
		keystoneService.addRole(Role.MEMBER, u.getOpenstackUser(), t.getOpenstatckTenant());
		log.debug("create user successfully for tenant : " + t.getName());
		return u;
	}
	
	private User fillUser(User user, Tenant t){
		user.setRoleId(Constants.ROLE_USER);
		user.setDefaultTenantId(t.getId());
		user.getTanents().clear();
		user.getTanents().add(t);
		
		return user;
	}

	@Override
	public void deleteUser(Integer userId) throws OpenstackAPIException, ApplicationException {
		if(userId==null) {
			log.info("Delete user failed for null is passed");
			return;
		}
		
		log.debug("Delete User");
		Tenant tenant = SecurityUtils.getTenant();
		if(tenant.getCreatorId()==userId){
			throw new ApplicationException(OpenstackUtil.getMessage("tenant.creator.cannot.delete"));
		}
		
		User user = userDao.findUser(userId);
		if(user == null){
			log.warn("No user instance found for user id : " + userId);
			return;
		}
		userDao.remove(user);
	}
	
}
