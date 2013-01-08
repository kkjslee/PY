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
	 * @return null if persist failed or self
	 */
	public User persist(User user);
}
