package com.inforstack.openstack.tenant;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface TenantService {
	
	/**
	 * create tenant
	 * @param tenant
	 * @return null if create failed
	 * @throws OpenstackAPIException 
	 */
	public Tenant createTenant(Tenant tenant) throws OpenstackAPIException;

	/**
	 * find tenant by id
	 * @param tenantId
	 * @return null if not found
	 */
	public Tenant findTenantById(Integer tenantId);

}
