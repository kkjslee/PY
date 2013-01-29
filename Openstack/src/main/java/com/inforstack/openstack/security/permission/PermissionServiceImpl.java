package com.inforstack.openstack.security.permission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	public static final Log log = LogFactory.getLog(PermissionServiceImpl.class);
	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	public Permission findPermissionById(int permissionId) {
		log.debug("Find permission by id : " + permissionId);
		Permission p = permissionDao.findById(permissionId);
		if(p==null){
			log.debug("Find permission failed");
		}else{
			log.debug("Find permission seccessfully");
		}
		
		return p;
	}

	@Override
	public List<Permission> findPermissions(String key) {
		log.debug("Find permission by key : " + key);
		List<Permission> p = permissionDao.findByKey(key);
		if(p==null || p.isEmpty()){
			log.debug("No permission found");
			return new ArrayList<Permission>();
		}else{
			log.debug("Find seccessfully");
			return p;
		}
	}
	
}
