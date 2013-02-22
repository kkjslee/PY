package com.inforstack.openstack.tld;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

public class Functions {

	public static String replaceAll(String string, String pattern, String replacement) {
        return string.replaceAll(pattern, replacement);
    }
	
	public static String append(String string, String append){
		return string+append;
	}
	
	public static List<String> addItem(List<String> list, String item){
		list.add(item);
		return list;
	}
	
	public static String label(String string, String defaultCode){
		if(StringUtil.isNullOrEmpty(string, false)){
			return OpenstackUtil.getMessage(defaultCode);
		}
		
		return string;
	}
	
	public static String value(String string, String defaultValue){
		if(string == null){
			return defaultValue;
		}
		
		return string;
	}
	
	public static String getProp(Object bean, String prop){
		if(StringUtil.isNullOrEmpty(prop, false) || bean == null) return "";
		
		Object ret = OpenstackUtil.getProperty(bean, prop);
		if(ret == null) return "";
		
		return ret.toString();
	}
	
	public static String propStr(String string, Object bean){
		if(StringUtil.isNullOrEmpty(string, false) || bean == null) return "";
		
		StringBuilder builder = new StringBuilder(string);
		Pattern pattern = Pattern.compile("\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(string);
		
		Stack<Replacer> stack = new Stack<Functions.Replacer>();
		int start = 0;
		while(matcher.find(start)){
			for(int i=0, n=matcher.groupCount(); i<n ;){
				String prop = matcher.group(++i);
				if(!OpenstackUtil.isValidProperty(prop)){
					start = matcher.start()+1;
					continue;
				}
				
				Object replacement = OpenstackUtil.getProperty(bean, prop);
				if(replacement == null) continue;
				
				stack.push(
					new Replacer(
						matcher.start(), 
						matcher.end(), 
						replacement.toString())
				);
				start = matcher.end();
			}
		}
		
		while(!stack.empty()){
			Replacer replacer = stack.pop();
			builder.replace(replacer.getStart(), replacer.getEnd(), replacer.getReplacement());
		}
		
		return builder.toString();
	}
	
	static class Replacer{
		private int start;
		private int end;
		private String replacement;
		
		Replacer(int start, int end, String replacement){
			this.start = start;
			this.end = end;
			this.replacement = replacement;
		}
		
		public int getStart() {
			return start;
		}
		public int getEnd() {
			return end;
		}
		public String getReplacement() {
			return replacement;
		}
		
		
	}
}
