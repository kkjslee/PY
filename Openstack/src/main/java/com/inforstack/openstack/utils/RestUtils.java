package com.inforstack.openstack.utils;

import java.io.IOException;
import java.net.URI;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.RequestBody;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.Access.Service;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint;
import com.inforstack.openstack.api.keystone.Access.Service.EndPoint.Type;

public class RestUtils {
	
	private static RestTemplate getTemplate(ClientHttpRequestFactory requestFactory) {
		RestTemplate template = new RestTemplate(requestFactory);
		for (HttpMessageConverter<?> converter : template.getMessageConverters()) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = jsonConverter.getObjectMapper();
				objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
				objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
			}
		}
		return template;
	}
	
	private static void addHeader(HttpHeaders headers) {
		headers.add("Content-type", "application/json");
		headers.add("Accept", "application/json");
	}
	
	public static String getEndpoint(Access access, String serviceName,Type type, String endpointSuffix) {
		String url = null;
		if (access != null && serviceName != null && type != null && endpointSuffix != null) {
			Service[] services = access.getServiceCatalog();
			if (services != null) {
				for (Service service : services) {
					if (service.getName().equalsIgnoreCase(serviceName)) {
						EndPoint[] endpoints = service.getEndpoints();
						if (endpoints.length > 0) {
							switch (type) {
							case ADMIN:
								url = endpoints[0].getAdminURL() + endpointSuffix;
								break;
							case INTERNAL:
								url = endpoints[0].getInternalURL() + endpointSuffix;
								break;
							case PUBLIC:
								url = endpoints[0].getPublicURL() + endpointSuffix;
								break;
							}
						}
						break;
					}
				}
			}
		}
		return url;
	}
	
	public static <Request extends RequestBody, Response> Response postForObject(String url, Request request, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers);
		
		try {
			RestTemplate template = getTemplate(new SimpleClientHttpRequestFactory());
			response = template.postForObject(url, new HttpEntity<Request>(request, headers), responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return response;
	}
	
	public static <Request extends RequestBody> URI postForLocation(String url, Request request, Object... urlVariables) throws OpenstackAPIException {
		URI uri = null;
		
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers);
		
		try {
			RestTemplate template = getTemplate(new SimpleClientHttpRequestFactory());
			uri = template.postForLocation(url, new HttpEntity<Request>(request, headers), urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return uri;
	}
	
	public static <Request extends RequestBody, Response> Response postForObject(String url, Access access, Request request, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers);
		
		if (access != null) {
			headers.add("X-Auth-Token", access.getToken().getId());
		}
		
		try {
			RestTemplate template = getTemplate(new SimpleClientHttpRequestFactory());
			response = template.postForObject(url, new HttpEntity<Request>(request, headers), responseType, urlVariables);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return response;
	}
	
	public static <Request extends RequestBody> URI postForLocation(String url, Access access, Request request, Object... urlVariables) throws OpenstackAPIException {
		URI uri = null;
		
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers);
		
		if (access != null) {
			headers.add("X-Auth-Token", access.getToken().getId());
		}
		
		try {
			RestTemplate template = getTemplate(new SimpleClientHttpRequestFactory());
			uri = template.postForLocation(url, new HttpEntity<Request>(request, headers), urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
		
		return uri;
	}
	
	public static <Request extends RequestBody> void put(String url, Access access, Request request, Object... urlVariables) throws OpenstackAPIException {
		HttpHeaders headers = new HttpHeaders();
		addHeader(headers);
		
		if (access != null) {
			headers.add("X-Auth-Token", access.getToken().getId());
		}
		
		try {
			RestTemplate template = getTemplate(new SimpleClientHttpRequestFactory());
			template.put(url, new HttpEntity<Request>(request, headers), urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[POST]:" + url, e);
		}
	}
	
	public static <Request, Response> Response get(String url, Class<Response> responseType, Object... urlVariables) throws OpenstackAPIException {
		Response response = null;
		
		try {
			ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
				@Override
				public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
					ClientHttpRequest request = super.createRequest(uri, httpMethod);
					HttpHeaders headers = request.getHeaders();
					addHeader(headers);
					return request;
				}
			};
			RestTemplate template = getTemplate(requestFactory);
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
					addHeader(headers);
					if (access != null) {
						headers.add("X-Auth-Token", access.getToken().getId());
					}
					return request;
				}
			};
			RestTemplate template = getTemplate(requestFactory);
			response = template.getForObject(url, responseType, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[GET]:" + url, e);
		}
		
		return response;
	}
	
	public static void delete(String url, final Access access, Object... urlVariables) throws OpenstackAPIException {
		try {
			ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory() {
				@Override
				public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
					ClientHttpRequest request = super.createRequest(uri, httpMethod);
					HttpHeaders headers = request.getHeaders();
					addHeader(headers);
					if (access != null) {
						headers.add("X-Auth-Token", access.getToken().getId());
					}
					return request;
				}
			};
			RestTemplate template = getTemplate(requestFactory);
			template.delete(url, urlVariables);
		} catch (Exception e) {
			throw new OpenstackAPIException("Can not fetch data[DELETE]:" + url, e);
		}
	}

}
