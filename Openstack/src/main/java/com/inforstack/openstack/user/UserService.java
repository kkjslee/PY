package com.inforstack.openstack.user;

import java.util.List;
import java.util.Set;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;

public interface UserService {

	public PaginationModel<User> pagination(int pageIndex, int pageSize);

	/**
	 * get all user permissions by user id
	 * 
	 * @param userId
	 * @return if no permission, an empty Set will return
	 */
	public Set<Permission> getPermissions(Integer userId);

	/**
	 * list all users
	 * 
	 * @return
	 */
	public List<User> listAll();

	/**
	 * find user by id
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(int userId);

	/**
	 * find user by user name
	 * 
	 * @param userName
	 * @return null if no user found
	 */
	public User findByName(String userName);

	/**
	 * find user by user name and email
	 * 
	 * @param userName
	 * @return
	 */
	public User findByNameAndEmail(String userName, String email);
	
	public String getOpenstackUserPassword();

	/**
	 * register user
	 * 
	 * @param user
	 * @param tenant
	 * @return User the user created or null for fail
	 * @throws OpenstackAPIException
	 * @throws ApplicationWarning
	 */
	public User registerUser(User user, Tenant tenant, int role)
			throws OpenstackAPIException;

	/**
	 * create user
	 * 
	 * @param user
	 * @param roleId
	 * @return null if create failed
	 * @throws OpenstackAPIException
	 */
	public User createUser(User user, int roleId) throws OpenstackAPIException;

	/**
	 * create tenant user, use the tenant stored in user detail
	 * 
	 * @param user
	 * @return
	 * @throws OpenstackAPIException
	 * @throws ApplicationRuntimeException
	 */
	public User createTenantUser(User user) throws OpenstackAPIException;

	/**
	 * update user
	 * 
	 * @param user
	 * @return
	 * @throws OpenstackAPIException
	 */
	public User updateUser(User user);

	/**
	 * delete user by id
	 * 
	 * @param userId
	 * @return The user deleted or null for fail
	 * @throws OpenstackAPIException
	 * @throws ApplicationRuntimeException
	 */
	public User deleteUser(int userId) throws OpenstackAPIException;

	/**
	 * check security question
	 * 
	 * @param username
	 * @param question
	 * @param answer
	 * @return
	 */
	public boolean checkQuestion(String username, String question, String answer);

	public void sendActiveUserEmail(Integer userId, String url);

	public User active(String mailCode, String random);

	public void sendResetPasswordEmail(Integer userId, String url);

	public User resetPassword(String mailCode, String random,
			String password);

	public User resetMail(String mailCode, String random, String verifyCode);

	public void sendChangeMailEmail(Integer userId, String mail, String url);

}
