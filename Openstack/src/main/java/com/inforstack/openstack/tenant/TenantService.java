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
	public Tenant findTenantById(int tenantId);
	
	/**
	 * find tenant by id, lazy loaded Promotion will be fetched
	 * @param tenantId
	 * @return
	 */
	public Tenant findTenantWithPromotion(int tenantId);
	
	public Tenant findAgent(int agentId);
	
	public Tenant updateTenant(Tenant tenant);
	
	public Tenant removeTenant(Tenant tenant);
	
	public Tenant removeTenant(int tenantId);
	
	public Tenant setPromotion(int tenantId, double discount);
}
