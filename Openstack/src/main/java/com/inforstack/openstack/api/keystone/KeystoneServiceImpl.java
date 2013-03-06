package com.inforstack.openstack.api.keystone;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;
import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;
import com.inforstack.openstack.utils.RestUtils;

@Service
@Transactional
public class KeystoneServiceImpl implements KeystoneService {
	
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Access>> accessMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Access>>();
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	private static final int hour = 1000 * 60 * 60;
	
	@Override
	public Access getAccess(String name, String pass, String tenant, boolean apply) throws OpenstackAPIException {		
		Access access = null;
		if (accessMap.containsKey(name)) {
			ConcurrentHashMap<String, Access> map = accessMap.get(name);
			if (map.containsKey(tenant)) {
				access = map.get(tenant);
				if (isExpired(access)) {
					access = null;
				}
			}
		}
		if (access == null && apply && pass != null) {
			access = applyAccess(name, pass, tenant);
		}
		return access;
	}
	
	@Override
	public boolean isExpired(Access access) {
		boolean expired = true;
		if (access != null && access.getToken().getExpires().after(new Date(System.currentTimeMillis() - hour))) {
			expired = false;
		}
		return expired;
	}
	
	public static final class TokenRequest implements RequestBody {
		
		public static final class Auth {
			
			public static final class Credentials {
				
				private String username;
				
				private String password;

				public String getUsername() {
					return username;
				}

				public void setUsername(String username) {
					this.username = username;
				}

				public String getPassword() {
					return password;
				}

				public void setPassword(String password) {
					this.password = password;
				}
				
			}
			
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

		private Auth auth;

		public Auth getAuth() {
			return auth;
		}

		public void setAuth(Auth auth) {
			this.auth = auth;
		}
		
	}
	
	public static final class TokenResponse {

		private Access access;

		public Access getAccess() {
			return access;
		}

		public void setAccess(Access access) {
			this.access = access;
		}
		
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
			
			TokenResponse response = RestUtils.postForObject(endpoint.getValue(), request, TokenResponse.class);
			if (response != null) {
				access = response.getAccess();
			}
			if (access != null) {
				ConcurrentHashMap<String, Access> map = null;
				if (accessMap.containsKey(name)) {
					map = accessMap.get(name);
				} else {
					map = new ConcurrentHashMap<String, Access>();
					accessMap.put(name, map);
				}
				map.put(response.getAccess().getUser().getName(), access);
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
	
	public static final class TenantsResponse {

		@JsonProperty("tenants_links")
		private String[] tenantsLinks;
		
		private Tenant[] tenants;

		public String[] getTenantsLinks() {
			return tenantsLinks;
		}

		public void setTenantsLinks(String[] tenantsLinks) {
			this.tenantsLinks = tenantsLinks;
		}

		public Tenant[] getTenants() {
			return tenants;
		}

		public void setTenants(Tenant[] tenants) {
			this.tenants = tenants;
		}
		
	}

	@Override
	public Tenant[] listTenants() throws OpenstackAPIException {
		Tenant[] tenants = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANTS);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				String url = getEndpoint(adminAccess, Type.ADMIN, endpointTenant.getValue());
				TenantsResponse response = RestUtils.get(url, adminAccess, TenantsResponse.class);
				if (response != null) {
					tenants = response.getTenants();
				}
			}
		}
		return tenants;
	}
	
	private static final class TenantBody implements RequestBody {
		
		private Tenant tenant;

		public Tenant getTenant() {
			return tenant;
		}

		public void setTenant(Tenant tenant) {
			this.tenant = tenant;
		}

	}
	
	@Override
	public Tenant createTenant(String name, String description, boolean enable) throws OpenstackAPIException {
		Tenant tenant = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANTS);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				String url = getEndpoint(adminAccess, Type.ADMIN, endpointTenant.getValue());
				try {
					Tenant newTenant = new Tenant();
					newTenant.setName(name);
					newTenant.setDescription(description);
					newTenant.setEnabled(enable);
					
					TenantBody body = new TenantBody();
					body.setTenant(newTenant);
					body = RestUtils.postForObject(url, adminAccess, body, TenantBody.class);
					tenant = body.getTenant();
				} catch (OpenstackAPIException e) {
					RestUtils.handleError(e);
				}
			}
		}
		return tenant;
	}
	
	@Override
	public Tenant updateTenant(Tenant tenant) throws OpenstackAPIException {
		Tenant newTenant = null;
		Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANT);
		if (endpointTenant != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				String url = getEndpoint(adminAccess, Type.ADMIN, endpointTenant.getValue());
				TenantBody body = new TenantBody();
				body.setTenant(tenant);
				body = RestUtils.postForObject(url, adminAccess, body, TenantBody.class, tenant.getId());
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
					String url = getEndpoint(adminAccess, Type.ADMIN, endpointTenant.getValue());
					RestUtils.delete(url, adminAccess, tenant.getId());
				}
			}
		}
	}
	
	private static final class UserBody implements RequestBody {
		
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
	}
	
	@Override
	public User createUser(String name, String pass, String email) throws OpenstackAPIException {
		User user = null;
		Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USERS);
		if (endpointUser != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				String url = getEndpoint(adminAccess, Type.ADMIN, endpointUser.getValue());
				User newUser = new User();
				newUser.setName(name);
				newUser.setPassword(pass);
				newUser.setEmail(email);
				newUser.setEnabled(true);
				
				UserBody body = new UserBody();
				body.setUser(newUser);
				body = RestUtils.postForObject(url, adminAccess, body, UserBody.class);
				user = body.getUser();
				user.setPassword(null);
			}
		}
		return user;
	}
	
	@Override
	public User updateUser(User user) throws OpenstackAPIException {
		User newUser = null;
		Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USER);
		if (endpointUser != null) {
			Access adminAccess = this.getAdminAccess();
			if (adminAccess != null) {
				String url = getEndpoint(adminAccess, Type.ADMIN, endpointUser.getValue());
				UserBody body = new UserBody();
				body.setUser(user);
				body = RestUtils.postForObject(url, adminAccess, body, UserBody.class, user.getId());
				newUser = body.getUser();
			}
		}
		return newUser;
	}
	
	@Override
	public void removeUser(User user) throws OpenstackAPIException {
		if (user != null && !user.getId().trim().isEmpty()) {
			Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USER);
			if (endpointUser != null) {
				Access adminAccess = this.getAdminAccess();
				if (adminAccess != null) {
					String url = getEndpoint(adminAccess, Type.ADMIN, endpointUser.getValue());
					RestUtils.delete(url, adminAccess, user.getId());
				}
			}
		}
	}
	
	@Override
	public void addRole(Role role, User user, Tenant tenant) throws OpenstackAPIException {
		if (user != null && !user.getId().trim().isEmpty() && tenant != null && !tenant.getId().trim().isEmpty()) {
			Configuration endpoint = this.configurationDao.findByName(ENDPOINT_ROLE);
			if (endpoint != null) {
				Access adminAccess = this.getAdminAccess();
				if (adminAccess != null) {
					String roleId = null;
					switch (role) {
					case ADMIN:
						roleId = this.configurationDao.findByName(ROLE_ID_ADMIN).getValue();
						break;
					case RESELL:
						roleId = this.configurationDao.findByName(ROLE_ID_RESELL).getValue();
						break;
					case MEMBER:
						roleId = this.configurationDao.findByName(ROLE_ID_MEMBER).getValue();
						break;
					}
					if (roleId != null) {
						String url = getEndpoint(adminAccess, Type.ADMIN, endpoint.getValue());
						RestUtils.put(url, adminAccess, null, tenant.getId(), user.getId(), roleId);
					} else {
						throw new OpenstackAPIException("Unknown role");
					}
				}
			}
		}
	}

	@Override
	public Access addUserAndTenant(String userName, String password, String email, String tenantName) throws OpenstackAPIException {
		Access access = null;
		if (userName != null && password != null && email != null && !userName.trim().isEmpty() && !password.trim().isEmpty()) {
			if (tenantName == null || tenantName.trim().isEmpty()) {
				tenantName = userName;
			}
			User newUser = null;
			Tenant newTenant = this.createTenant(tenantName, "Tenant for user[" + userName + "]", true);
			if (newTenant != null) {
				try {
					newUser = this.createUser(userName, password, email);
				} catch (OpenstackAPIException e) {
					this.removeTenant(newTenant);
					throw new OpenstackAPIException("Fail to create user: " + userName, e);
				}
			}
			if (newUser != null) {
				try {
					this.addRole(Role.MEMBER, newUser, newTenant);
					access = this.getAccess(userName, password, newTenant.getId(), true);
				} catch (OpenstackAPIException e) {
					this.removeUser(newUser);
					this.removeTenant(newTenant);
					throw new OpenstackAPIException("Fail to add role" + e);
				}
			}
		}
		return access;
	}

	@Override
	public void removeUserAndTenant(String userId, String tenantId) throws OpenstackAPIException {
		Access adminAccess = this.getAdminAccess();
		if (tenantId != null && !tenantId.trim().isEmpty()) {
			Configuration endpointTenant = this.configurationDao.findByName(ENDPOINT_TENANT);
			if (endpointTenant != null) {
				if (adminAccess != null) {
					String url = getEndpoint(adminAccess, Type.ADMIN, endpointTenant.getValue());
					RestUtils.delete(url, adminAccess, tenantId);
				}
			}
		}		
		if (userId != null && !userId.trim().isEmpty()) {
			Configuration endpointUser = this.configurationDao.findByName(ENDPOINT_USER);
			if (endpointUser != null) {
				if (adminAccess != null) {
					String url = getEndpoint(adminAccess, Type.ADMIN, endpointUser.getValue());
					RestUtils.delete(url, adminAccess, userId);
				}
			}
		}		
	}
	
	private static String getEndpoint(Access access, Type type, String suffix) {
		return RestUtils.getEndpoint(access, "keystone", type, suffix);
	}

}
