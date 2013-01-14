package com.inforstack.openstack.user;

import java.util.List;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;

public interface UserService {

	/**
	 * get all user permissions by user id
	 * @param userId
	 * @return if no permission, an empty list will return
	 */
	public List<Permission> getPermissions(Integer userId);
	
	/**
	 * find user by user name
	 * @param userName
	 * @return null if no user found
	 */
	public User findByName(String userName);
	
	/**
	 * register user
	 * @param user
	 * @param tenant
	 * @return null if create failed
	 */
	public User registerUser(User user, Tenant tenant) throws OpenstackAPIException;

	/**
	 * create user
	 * @param user
	 * @return null if create failed
	 * @throws OpenstackAPIException 
	 */
	public User createUser(User user) throws OpenstackAPIException;

	public User updateUser(User user);

}
