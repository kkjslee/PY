package com.inforstack.openstack.security.permission;

import java.util.List;

public interface PermissionDao {

	public Permission findById(Integer permissionId);

	public List<Permission> findByKey(String key);

}
