package com.inforstack.openstack.tenant;

public interface TenantDao {

	/**
	 * persist the given tenant
	 * @param tenant
	 * @return null if error or the pass tenant
	 */
	public Tenant persist(Tenant tenant);
	
	/**
	 * find tenant by tenant id
	 * @param tenantId
	 * @return null if not found
	 */
	public Tenant findById(Integer tenantId);

}
