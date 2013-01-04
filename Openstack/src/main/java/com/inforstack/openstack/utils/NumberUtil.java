package com.inforstack.openstack.utils;

public class NumberUtil {

	
	public static Integer getInteger(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return null;
		}
	}
}
