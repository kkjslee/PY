package com.inforstack.openstack.user;

import java.util.List;

import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;

public interface UserService {

	/**
	 * get all user permissions by user id
	 * @param userId
	 * @return
	 */
	public List<Permission> getPermissions(Integer userId);
	
	/**
	 * find user by user name
	 * @param userName
	 * @return
	 */
	public User findByName(String userName);
	
	/**
	 * register user
	 * @param user
	 * @param tenant
	 * @return
	 */
	public User registerUser(User user, Tenant tenant);

	/**
	 * create user
	 * @param user
	 * @return
	 */
	public User createUser(User user);

}
