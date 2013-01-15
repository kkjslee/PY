package com.inforstack.openstack.user;

import java.util.Set;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;

public interface UserService {

	/**
	 * get all user permissions by user id
	 * @param userId
	 * @return if no permission, an empty Set will return
	 */
	public Set<Permission> getPermissions(Integer userId);
	
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
	 * @throws OpenstackAPIException
	 * @throws ApplicationWarning
	 */
	public void registerUser(User user, Tenant tenant) throws OpenstackAPIException, ApplicationException;
	
	/**
	 * create user
	 * @param user
	 * @return null if create failed
	 * @throws OpenstackAPIException 
	 */
	public User createUser(User user) throws OpenstackAPIException;
	
	/**
	 * create tenant user, use the tenant stored in user detail
	 * @param user
	 * @return
	 * @throws OpenstackAPIException
	 */
	public User createTenantUser(User user) throws ApplicationException, OpenstackAPIException;

	public User updateUser(User user) throws OpenstackAPIException;

	public void deleteUser(Integer userId) throws OpenstackAPIException, ApplicationException;
}
