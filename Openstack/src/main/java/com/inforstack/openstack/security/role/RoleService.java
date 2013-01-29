package com.inforstack.openstack.security.role;


public interface RoleService {

	/**
	 * find role by id
	 * @param roleId
	 * @return
	 */
	public Role findRoleById(int roleId);

}
