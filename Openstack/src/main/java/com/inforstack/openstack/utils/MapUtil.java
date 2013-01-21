package com.inforstack.openstack.utils;

import java.util.Map;

public class MapUtil {
	
	public static boolean isNullOrEmpty(Map<? extends Object, ? extends Object> map){
		if(map == null || map.isEmpty()){
			return true;
		}
		
		return false;
	}

}
