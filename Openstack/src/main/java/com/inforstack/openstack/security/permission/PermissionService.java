package com.inforstack.openstack.security.permission;

import java.util.List;

public interface PermissionService {
	
	/**
	 * find permission by id
	 * @param permissionId
	 * @return
	 */
	public Permission findPermissionById(int permissionId);
	
	/**
	 * search the name of permission by key
	 * @param key String like *key*
	 * @return
	 */
	public List<Permission> findPermissions(String key);

}
