package com.inforstack.openstack.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.permission.Permission;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Permission> getPermissions(Integer userId) {
		User user = userDao.findUser(userId);
		List<SecurityGroup> sgs = user.getSecurityGroups();
		List<Permission> permissions = new ArrayList<Permission>();
		if(sgs==null) return permissions;
		for(SecurityGroup sg : sgs){
			List<Permission> pLst = sg.getPermissions();
			if(pLst==null) continue;
			permissions.addAll(pLst);
		}
		
		return permissions;
	}

	@Override
	public User findByName(String userName) {
		return userDao.findByName(userName);
	}

}
