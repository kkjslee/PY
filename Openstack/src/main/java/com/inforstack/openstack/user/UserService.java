package com.inforstack.openstack.user;

import java.util.List;

import com.inforstack.openstack.security.permission.Permission;




public interface UserService {

	public List<Permission> getPermissions(Integer userId);
	
	public User findByName(String userName);

}
