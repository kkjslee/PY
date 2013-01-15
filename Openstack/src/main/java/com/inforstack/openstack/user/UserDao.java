package com.inforstack.openstack.user;

public interface UserDao {

	/**
	 * find user by user id
	 * @param userId
	 * @return null if not found
	 */
	public User findUser(Integer userId);

	
	/**
	 * find user by user name
	 * @param userName
	 * @return null if not found
	 */
	public User findByName(String userName);

	/**
	 * persist user
	 * @param user
	 */
	public void persist(User user);

	/**
	 * update user
	 * @param user
	 * @return
	 */
	public User merge(User user);
	
	/**
	 * remove user
	 * @param user
	 */
	public void remove(User user);
}
