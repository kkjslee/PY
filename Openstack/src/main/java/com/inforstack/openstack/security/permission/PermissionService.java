package com.inforstack.openstack.security.permission;

import java.util.List;

public interface PermissionService {
	
	public Permission findPermissionById(Integer permissionId);
	
	/**
	 * 
	 * @param key String like *key*
	 * @return
	 */
	public List<Permission> findPermissions(String key);

}
