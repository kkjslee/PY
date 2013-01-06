package com.inforstack.openstack.api.keystone;

public class Auth {
	
	private Credentials passwordCredentials;
	
	private String tenantId;

	public Credentials getPasswordCredentials() {
		return passwordCredentials;
	}

	public void setPasswordCredentials(Credentials passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}