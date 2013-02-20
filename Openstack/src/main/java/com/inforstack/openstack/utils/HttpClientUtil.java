package com.inforstack.openstack.utils;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class HttpClientUtil {
	
	public static boolean checkRedirectLocation(HttpResponse response, String host, String location) {
		boolean success = false;
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase("Location") && header.getValue().equalsIgnoreCase(host + location)) {
				success = true;
				break;
			}
		}
		return success;
	}
		
	public static String getSession(HttpResponse response) {
		String session = null;
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase("Set-Cookie")) {
				String value = header.getValue();
				session = value.substring(0, value.indexOf(';'));
				break;
			}
		}
		return session;
	}
	
	public static <T> T getObject(String json, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		T instance = null;
		try {
			instance = mapper.readValue(json, type);
		} catch (JsonParseException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		return instance;
	}
	
}
