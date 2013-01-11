package com.inforstack.openstack.utils;

public class StringUtil {
	
	public static final boolean isNullOrEmpty(String s){
		return isNullOrEmpty(s, true);
	}
	
	public static final boolean isNullOrEmpty(String s, boolean doTrim){
		if(s==null) return true;
		
		if(doTrim) s = s.trim();
		
		if(s.length() == 0) return true;
		return false;
	}
	
	public static String trimString(String s){
		if(s==null){
			return null;
		}else {
			return s.trim();
		}
	}
}
