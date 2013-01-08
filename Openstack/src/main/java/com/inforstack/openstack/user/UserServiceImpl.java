package com.inforstack.openstack.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;


@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Log log = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private TenantService tenantService;
	
	@Override
	public List<Permission> getPermissions(Integer userId) {
		log.debug("get permission by user id : " +userId);
		User user = userDao.findUser(userId);
		List<SecurityGroup> sgs = (user==null? null : user.getSecurityGroups());
		List<Permission> permissions = new ArrayList<Permission>();
		if(sgs==null) {
			log.debug("no security group found for user id : " +userId);
			return permissions;
		}
		for(SecurityGroup sg : sgs){
			List<Permission> pLst = sg.getPermissions();
			if(pLst==null) continue;
			permissions.addAll(pLst);
		}
		log.debug(permissions.size()+ " permissions found for user id : " +userId);
		
		return permissions;
	}

	@Override
	public User findByName(String userName) {
		log.debug("find user by name : " +userName);
		User user = userDao.findByName(userName);
		if(user==null){
			log.debug("No user found by name : " + userName);
		}else{
			log.debug("Find user successfully");
		}
		
		return user;
	}

	@Override
	public User registerUser(User user, Tenant tenant) {
		if(user==null || tenant==null){
			log.debug("Register user failed for passed user/tenant is null");
			return null;
		}
		
		log.debug("register user : " + user.getName() +", tenant : " + tenant.getName());
		Tenant t = tenantService.createTenant(tenant);
		if(t == null){
			log.debug("create tenant failed" + tenant.getName());
			return null;
		}
		
		UserService self = (UserService)OpenstackUtil.getBean("UserService");
		User u = self.createUser(user);
		if(u == null){
			log.debug("create user failed" + user.getName());
			return null;
		}
		List<Tenant> tlst = new ArrayList<Tenant>();
		tlst.add(t);
		u.setTanents(tlst);
		
		return user;
	}

	@Override
	public User createUser(User user) {
		if(user==null) {
			log.debug("Create user failed for null is passed");
			return null;
		}
		
		log.debug("Create user : "+user.getName());
		user.setStatus(Constants.USER_STATUS_VALID);
		user.setAgeing(Constants.USER_AGEING_ACTIVE);
		user.setCreateTime(new Date());
		User u = userDao.persist(user);
		if(u == null){
			log.debug("Create user failed");
			return null;
		}else{
			log.debug("create user successfully");
			return u;
		}
	}

}
