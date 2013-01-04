package com.inforstack.openstack.user;

import java.util.List;

import com.inforstack.openstack.security.group.SecurityGroup;




public interface UserDao {

	public User findUser(Integer userId);

	public User findByName(String userName);
}
