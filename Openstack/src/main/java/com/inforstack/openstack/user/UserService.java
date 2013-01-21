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
	 * @return User the user created or null for fail
	 * @throws OpenstackAPIException
	 * @throws ApplicationWarning
	 */
	public User registerUser(User user, Tenant tenant, int role) throws OpenstackAPIException;
	
	/**
	 * create user
	 * @param user
	 * @param roleId
	 * @return null if create failed
	 * @throws OpenstackAPIException 
	 */
	public User createUser(User user, int roleId) throws OpenstackAPIException;
	
	/**
	 * create tenant user, use the tenant stored in user detail
	 * @param user
	 * @return
	 * @throws OpenstackAPIException
	 */
	public User createTenantUser(User user) throws ApplicationException, OpenstackAPIException;

	public User updateUser(User user) throws OpenstackAPIException;

	/**
	 * 
	 * @param userId
	 * @return The user deleted or null for fail
	 * @throws OpenstackAPIException
	 * @throws ApplicationException
	 */
	public User deleteUser(Integer userId) throws OpenstackAPIException, ApplicationException;
}
