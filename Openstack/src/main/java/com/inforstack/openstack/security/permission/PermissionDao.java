package com.inforstack.openstack.security.permission;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface PermissionDao extends IDao<Permission> {

	public List<Permission> findByKey(String key);

}
