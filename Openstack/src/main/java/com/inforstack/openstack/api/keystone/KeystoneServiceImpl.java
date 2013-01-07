package com.inforstack.openstack.api.keystone;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class KeystoneServiceImpl implements KeystoneService {
	
	private static ConcurrentHashMap<String, Access> accessMap = new ConcurrentHashMap<String, Access>();
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Override
	public Access getAccess(String name, String pass, String tenant, boolean apply) throws OpenstackAPIException {		
		Access access = null;
		if (accessMap.containsKey(name)) {
			access = accessMap.get(name);
			if (isExpired(access)) {
				access = null;
			}
		}
		if (access == null && apply) {
			access = applyAccess(name, pass, tenant);
		}
		return access;
	}
	
	@Override
	public boolean isExpired(Access access) {
		boolean expired = true;
		if (access != null && access.getToken().getExpires().after(new Date())) {
			expired = false;
		}
		return expired;
	}

	@Override
	public Access applyAccess(String name, String pass, String tenant) throws OpenstackAPIException {
		Access access = null;
		
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_TOKENS);
		if (endpoint != null) {
			TokenRequest.Auth.Credentials credentials = new TokenRequest.Auth.Credentials();
			credentials.setUsername(name);
			credentials.setPassword(pass);
			TokenRequest.Auth auth = new TokenRequest.Auth();
			auth.setPasswordCredentials(credentials);
			auth.setTenantId(tenant);
			
			TokenRequest request = new TokenRequest();
			request.setAuth(auth);
			
			TokenResponse response = RestUtils.post(endpoint.getValue(), request, TokenResponse.class);
			if (response != null) {
				access = response.getAccess();
			}
			if (access != null) {
				accessMap.put(response.getAccess().getUser().getName(), access);
			}
		}
		
		return access;
	}

	@Override
	public Access getAdminAccess() throws OpenstackAPIException {
		Access access = null;
		Configuration tenant = this.configurationDao.findByName(TENANT_ADMIN_ID);
		Configuration adminName = this.configurationDao.findByName(USER_ADMIN_NAME);
		Configuration adminPass = this.configurationDao.findByName(USER_ADMIN_PASS);
		if (tenant != null && adminName != null && adminPass != null) {
			access = this.getAccess(adminName.getValue(), adminPass.getValue(), tenant.getValue(), true);
		}
		return access;
	}

	@Override
	public Tenant[] getTenants() throws OpenstackAPIException {
		Tenant[] tenants = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANTS);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				TenantsResponse response = RestUtils.get(endpointTenant.getValue(), adminAccess, TenantsResponse.class);
				if (response != null) {
					tenants = response.getTenants();
				}
			}
		}
		return tenants;
	}
	
	@Override
	public Tenant addTenant(String name, String description, boolean enable) throws OpenstackAPIException {
		Tenant tenant = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANTS);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				Tenant newTenant = new Tenant();
				newTenant.setName(name);
				newTenant.setDescription(description);
				newTenant.setEnabled(enable);
				
				TenantBody body = new TenantBody();
				body.setTenant(newTenant);
				body = RestUtils.post(endpointTenant.getValue(), adminAccess, body, TenantBody.class);
				tenant = body.getTenant();
			}
		}
		return tenant;
	}
	
	public Tenant updateTenant(Tenant tenant) throws OpenstackAPIException {
		Tenant newTenant = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANT);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				TenantBody body = new TenantBody();
				body.setTenant(tenant);
				body = RestUtils.post(endpointTenant.getValue(), adminAccess, body, TenantBody.class, tenant.getId());
				newTenant = body.getTenant();
			}
		}
		return newTenant;
	}
	
	@Override
	public void removeTenant(Tenant tenant) throws OpenstackAPIException {
		if (tenant != null && !tenant.getId().trim().isEmpty()) {
			Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANT);
			if (endpointTenant != null) {
				Access adminAccess = this.getAdminAccess();
				if (adminAccess != null) {
					RestUtils.delete(endpointTenant.getValue(), adminAccess, tenant.getId());
				}
			}
		}
	}
	
//	public User addUser(String name, String pass, String email) throws OpenstackAPIException {
//		User user = null;
//		Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USERS);
//		if (endpointUser != null) {
//			Access adminAccess = this.getAdminAccess();
//			if (adminAccess != null) {
//				User newUser = new User();
//				newUser.setName(name);
//				newUser.set
//			}
//		}
//		return user;
//	}

	@Override
	public void addUserAndTenant(String name, String pass, String email) throws OpenstackAPIException {
		if (name != null && pass != null && email != null && !name.trim().isEmpty() && !pass.trim().isEmpty()) {
			Tenant tenant = this.addTenant(name, "", true);
			if (tenant != null) {
				
			}
		}
	}

	@Override
	public void removeUserAndTenant(String name) throws OpenstackAPIException {
		// TODO Auto-generated method stub
		
	}

}
