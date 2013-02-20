package com.inforstack.openstack.user;

import com.inforstack.openstack.basic.BasicDao;

public interface UserDao extends BasicDao<User> {

	/**
	 * find user by user name
	 * 
	 * @param userName
	 * @return null if not found
	 */
	public User findByName(String userName);

	public User findByNameAndEmail(String userName, String email);

}
