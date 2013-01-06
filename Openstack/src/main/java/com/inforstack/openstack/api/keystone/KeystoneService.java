package com.inforstack.openstack.api.keystone;

import com.inforstack.openstack.api.OpenstackAPIException;

public interface KeystoneService {
	
	public final static String ENDPOINT_TOKEN	= "openstack.endpoint.token";
	public final static String ENDPOINT_TENANT	= "openstack.endpoint.tenant";
	public final static String ENDPOINT_USER	= "openstack.endpoint.user";
	public final static String TENANT_ADMIN_ID	= "openstack.tenant.admin";
	public final static String USER_ADMIN_NAME	= "openstack.user.admin.name";
	public final static String USER_ADMIN_PASS	= "openstack.user.admin.pass";

	public Access getAccess(String name, String pass, String tenant, boolean apply) throws OpenstackAPIException;
	
	public boolean isExpired(Access access);
	
	public Access applyAccess(String name, String pass, String tenant) throws OpenstackAPIException;
	
	public Access getAdminAccess() throws OpenstackAPIException;
	
	public void addUserAndTenant(String name, String pass, String email) throws OpenstackAPIException;
	
}
