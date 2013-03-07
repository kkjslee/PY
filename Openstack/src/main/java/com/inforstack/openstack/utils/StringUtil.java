package com.inforstack.openstack.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
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
	
	public static String file2String(File file, String encoding) { 
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		try {
			if (encoding == null || "".equals(encoding.trim())) {
				reader = new InputStreamReader(new FileInputStream(file),
						encoding);
			} else {
				reader = new InputStreamReader(new FileInputStream(file));
			}
			char[] buffer = new char[4096];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return writer.toString();
	}
	 
	public static boolean string2File(String text, File distFile) { 
		if (!distFile.getParentFile().exists())
			distFile.getParentFile().mkdirs();
		BufferedReader br = null;
		BufferedWriter bw = null;
		boolean flag = true;
		try {
			br = new BufferedReader(new StringReader(text));
			bw = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024 * 64];
			int len;
			while ((len = br.read(buf)) != -1) {
				bw.write(buf, 0, len);
			}
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			flag = false;
		}
		return flag;
} 
}
