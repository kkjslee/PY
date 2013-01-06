package com.inforstack.openstack.api.token;

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
		
		Configuration endpoint = this.configurationDao.findByName(ENDPOINT_TOKEN);
		if (endpoint != null) {
			Credentials credentials = new Credentials();
			credentials.setUsername(name);
			credentials.setPassword(pass);
			Auth auth = new Auth();
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
	public void addUserAndTenant(String name, String pass, String email) throws OpenstackAPIException {
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANT);
		Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USER);
		if (endpointTenant != null && endpointUser != null) {
			
		}
	}

}
