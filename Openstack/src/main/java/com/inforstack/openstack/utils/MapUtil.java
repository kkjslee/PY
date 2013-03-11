package com.inforstack.openstack.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	
	public static boolean isNullOrEmpty(Map<? extends Object, ? extends Object> map){
		if(map == null || map.isEmpty()){
			return true;
		}
		
		return false;
	}
	
	public static String buildPropStr(Map<String, String> properties){
		if(isNullOrEmpty(properties)){
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		for(String key : properties.keySet()){
			String value = properties.get(key);
			if(StringUtil.isNullOrEmpty(value)){
				continue;
			}
			
			builder.append(key).append('=').append(value).append(';');
		}
		
		return builder.toString();
	}
	
	public static Map<String, String> buildPropMap(String s){
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtil.isNullOrEmpty(s)){
			return map;
		}
		
		for(String prop : s.split(";")){
			if(StringUtil.isNullOrEmpty(prop)){
				continue;
			}
			
			String[] keyValue = prop.split("=");
			if(keyValue.length != 2){
				continue;
			}
			
			map.put(keyValue[0], keyValue[1]);
		}
		
		return map;
	}
}
