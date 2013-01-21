package com.inforstack.openstack.tenant;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface TenantService {
	
	/**
	 * create tenant
	 * @param tenant
	 * @param roleId
	 * @return null if create failed
	 * @throws OpenstackAPIException 
	 */
	public Tenant createTenant(Tenant tenant, int roleId) throws OpenstackAPIException;

	/**
	 * find tenant by id
	 * @param tenantId
	 * @return null if not found
	 */
	public Tenant findTenantById(Integer tenantId);
	
	/**
	 * find tenant by id, lazy loaded Promotion will be fetched
	 * @param tenantId
	 * @return
	 */
	public Tenant findTenantWithPromotion(Integer tenantId);
	
	public Tenant findAgent(Integer agentId);
	
	public Tenant updateTenant(Tenant tenant);
	
	public Tenant removeTenant(Tenant tenant);
	
	public Tenant removeTenant(Integer tenantId);
	
	public Tenant setPromotion(Integer tenantId, double discount);
}
