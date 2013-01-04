package com.inforstack.openstack.security.role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role findRoleById(Integer roleId) {
		return roleDao.findById(roleId);
	}
	
}
