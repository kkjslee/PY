package com.inforstack.openstack.user;

import com.inforstack.openstack.utils.db.IDao;

public interface UserDao extends IDao<User> {

	
	/**
	 * find user by user name
	 * @param userName
	 * @return null if not found
	 */
	public User findByName(String userName);

}
