package com.inforstack.openstack.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	public static boolean isNullOrEmpty(Collection<? extends Object> collection){
		if(collection==null || collection.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static String toString(Collection<? extends Object> collection){
		if(collection == null){
			return "";
		}else{
			return collection.toString();
		}
	}

	public static Map<String, String> collectionToMap(
			List<? extends Object> props, String key, String value) {
		Map<String, String> map = new HashMap<String, String>();
		if(props == null){
			return map;
		}
		
		for(Object o : props){
			String k = OpenstackUtil.setProperty(key, o);
			String v = OpenstackUtil.setProperty(value, o);
			if(!StringUtil.isNullOrEmpty(k)){
				map.put(k, v);
			}
		}
		
		return map;
	}
}
