package com.inforstack.openstack.utils;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.inforstack.openstack.api.token.Auth;
import com.inforstack.openstack.api.token.Credentials;
import com.inforstack.openstack.api.token.Token;
import com.inforstack.openstack.api.token.TokenRequest;
import com.inforstack.openstack.api.token.TokenResponse;

public class TokenUtils {
	
	private static ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<String, Token>();
	
	private static String url = "http://192.168.1.145:5000/v2.0/tokens";
	
	public static Token getToken(String name, String pass, String tenant, boolean apply) {
		Token token = null;
		if (tokens.containsKey(name)) {
			token = tokens.get(name);
			if (isExpired(token)) {
				token = null;
			}
		}
		if (token == null && apply) {
			token = applyToken(name, pass, tenant);
		}
		return token;
	}
	
	public static boolean isExpired(Token token) {
		boolean expired = true;
		if (token != null && token.getExpires().after(new Date())) {
			expired = false;
		}
		return expired;
	}
	
	public static Token applyToken(String name, String pass, String tenant) {
		Token token = null;
		
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
			TokenResponse response = template.postForObject(url, new HttpEntity<TokenRequest>(request, headers), TokenResponse.class);
			if (response != null) {
				token = response.getAccess().getToken();
			}
			if (token != null) {
				tokens.put(response.getAccess().getUser().getName(), token);
			}
		} catch (Exception e) {
		}
		return token;
	}

}
