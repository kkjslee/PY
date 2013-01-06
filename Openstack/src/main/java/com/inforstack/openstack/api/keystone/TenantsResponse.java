package com.inforstack.openstack.api.keystone;

public class TenantsResponse {

	private String[] tenants_links;
	
	private Tenant[] tenants;

	public String[] getTenants_links() {
		return tenants_links;
	}

	public void setTenants_links(String[] tenants_links) {
		this.tenants_links = tenants_links;
	}

	public Tenant[] getTenants() {
		return tenants;
	}

	public void setTenants(Tenant[] tenants) {
		this.tenants = tenants;
	}
	
}
