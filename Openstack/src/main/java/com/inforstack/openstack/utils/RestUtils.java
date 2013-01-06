package com.inforstack.openstack.utils;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;

public class RestUtils {
	
	public static <Request, Response> Response post(String url, Request request, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Accept", "application/json");
		
		try {
			RestTemplate template = new RestTemplate();
			response = template.postForObject(url, new HttpEntity<Request>(request, headers), responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return response;
	}
	
	public static <Request, Response> Response post(String url, Access access, Request request, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Accept", "application/json");
		if (access != null) {
			headers.add("X-Auth-Token", access.getToken().getId());
		}
		
		try {
			RestTemplate template = new RestTemplate();
			response = template.postForObject(url, new HttpEntity<Request>(request, headers), responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return response;
	}
	
	public static <Request, Response> Response get(String url, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		try {
			ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
				@Override
				public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
					ClientHttpRequest request = super.createRequest(uri, httpMethod);
					HttpHeaders headers = request.getHeaders();
					headers.add("Content-type", "application/json");
					headers.add("Accept", "application/json");
					return request;
				}
			};
			RestTemplate template = new RestTemplate(requestFactory);
			response = template.getForObject(url, responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[GET]:" + url, e);
		}
		
		return response;
	}
	
	public static <Request, Response> Response get(String url, final Access access, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		try {
			ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
				@Override
				public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
					ClientHttpRequest request = super.createRequest(uri, httpMethod);
					HttpHeaders headers = request.getHeaders();
					headers.add("Content-type", "application/json");
					headers.add("Accept", "application/json");
					if (access != null) {
						headers.add("X-Auth-Token", access.getToken().getId());
					}
					return request;
				}
			};
			RestTemplate template = new RestTemplate(requestFactory);
			response = template.getForObject(url, responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[GET]:" + url, e);
		}
		
		return response;
	}

}
