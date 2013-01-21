package com.inforstack.openstack.tenant;

public interface TenantDao {

	/**
	 * persist the given tenant
	 * @param tenant
	 * @return null if error or the pass tenant
	 */
	public void persist(Tenant tenant);
	
	/**
	 * find tenant by tenant id
	 * @param tenantId
	 * @return null if not found
	 */
	public Tenant findById(Integer tenantId);

	public void remove(Tenant tenant);

	public void merge(Tenant tenant);

}
