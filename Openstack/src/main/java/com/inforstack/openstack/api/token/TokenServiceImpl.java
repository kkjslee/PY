package com.inforstack.openstack.api.token;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.inforstack.openstack.configuration.Configuration;
import com.inforstack.openstack.configuration.ConfigurationDao;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {
	
	private static ConcurrentHashMap<String, Access> accessMap = new ConcurrentHashMap<String, Access>();
	
	@Autowired
	private ConfigurationDao configurationDao;
	
	@Override
	public Access getAccess(String name, String pass, String tenant, boolean apply) {		
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
	public Access applyAccess(String name, String pass, String tenant) {
		Access access = null;
		
		Configuration configuration = this.configurationDao.findByName(ENDPOINT_TOKEN);
		if (configuration != null) {
			Credentials credentials = new Credentials();
			credentials.setUsername(name);
			credentials.setPassword(pass);
			Auth auth = new Auth();
			auth.setPasswordCredentials(credentials);
			auth.setTenantId(tenant);
			
			TokenRequest request = new TokenRequest();
			request.setAuth(auth);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json");
			headers.add("Accept", "application/json");
			
			try {
				RestTemplate template = new RestTemplate();
				TokenResponse response = template.postForObject(configuration.getValue(), new HttpEntity<TokenRequest>(request, headers), TokenResponse.class);
				if (response != null) {
					access = response.getAccess();
				}
				if (access != null) {
					accessMap.put(response.getAccess().getUser().getName(), access);
				}
			} catch (Exception e) {
			}
		}
		
		return access;
	}

}
