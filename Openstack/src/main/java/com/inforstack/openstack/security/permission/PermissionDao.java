package com.inforstack.openstack.security.permission;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface PermissionDao extends BasicDao<Permission> {

	public List<Permission> findByKey(String key);

}
