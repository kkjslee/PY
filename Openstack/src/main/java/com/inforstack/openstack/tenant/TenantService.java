package com.inforstack.openstack.tenant;

public interface TenantService {
	
	/**
	 * create tenant
	 * @param tenant
	 * @return null if create failed
	 */
	public Tenant createTenant(Tenant tenant);

	/**
	 * find tenant by id
	 * @param tenantId
	 * @return null if not found
	 */
	public Tenant findTenantById(Integer tenantId);

}
