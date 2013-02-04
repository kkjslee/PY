package com.inforstack.openstack.security.role;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;


@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	
	private static final Logger log = new Logger(RoleServiceImpl.class);
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role findRoleById(int roleId) {
		log.debug("Find role by id : " + roleId);
		Role role = roleDao.findById(roleId);
		if(role==null){
			log.info("Find role failed by role id : " + roleId);
		}else{
			log.debug("Find successfully");
		}
		
		return role;
	}
	
}
