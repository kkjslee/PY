package com.inforstack.openstack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

	public static final boolean isNullOrEmpty(String s) {
		return isNullOrEmpty(s, true);
	}

	public static final boolean isNullOrEmpty(String s, boolean doTrim) {
		if (s == null)
			return true;

		if (doTrim)
			s = s.trim();

		if (s.length() == 0)
			return true;
		return false;
	}

	public static String trimString(String s) {
		if (s == null) {
			return null;
		} else {
			return s.trim();
		}
	}

	public static String convertToLogString(String s) {
		if (s == null) {
			return "";
		} else {
			if (s.length() < 50) {
				return s;
			} else {
				return s.substring(0, 50) + "...";
			}
		}
	}

	public static String getStringFromEmpty(String s) {
		if (isNullOrEmpty(s)) {
			return "";
		} else {
			return s;
		}
	}

	public static String joinStringWithEmpty(String... s) {
		StringBuilder sBuilder = new StringBuilder();
		if (s.length > 0) {
			for (String ele : s) {
				sBuilder = sBuilder.append(getStringFromEmpty(ele));
			}
			return sBuilder.toString();
		} else {
			return null;
		}

	}

	public static String formateDateString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

		return format.format(date);
	}
	
	public static String leftPadding(String s, char c, int length){
		if(s == null){
			s = "";
		}
		StringBuilder ret = new StringBuilder();
		for(int i=0, n=(length-s.length());i<n;i++){
			ret.append(c);
		}
		ret.append(s);
		return ret.toString();
	}

}
