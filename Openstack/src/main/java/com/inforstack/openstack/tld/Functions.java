package com.inforstack.openstack.tld;

import java.text.DecimalFormat;
import java.util.List;

import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

public class Functions {

	public static String replaceAll(String string, String pattern,
			String replacement) {
		return string.replaceAll(pattern, replacement);
	}

	public static String append(String string, String append) {
		return string + append;
	}

	public static List<String> addItem(List<String> list, String item) {
		list.add(item);
		return list;
	}

	public static String label(String string, String defaultCode) {
		if (StringUtil.isNullOrEmpty(string, false)) {
			return OpenstackUtil.getMessage(defaultCode);
		}

		return string;
	}

	public static String value(String string, String defaultValue) {
		if (StringUtil.isNullOrEmpty(string, false)) {
			return defaultValue;
		}

		return string;
	}

	public static String money(String string) {
		try {
			double d = new Double(string);
			return new DecimalFormat("#.00").format(d);
		} catch (RuntimeException re) {
			return string;
		}
	}

	public static String getProp(Object bean, String prop) {
		if (StringUtil.isNullOrEmpty(prop, false) || bean == null)
			return "";

		Object ret = OpenstackUtil.getProperty(bean, prop);
		if (ret == null)
			return "";

		return ret.toString();
	}

	public static String propStr(String string, Object bean) {
		return OpenstackUtil.setProperty(string, bean);
	}

}
