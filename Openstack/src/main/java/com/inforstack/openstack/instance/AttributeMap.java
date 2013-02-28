package com.inforstack.openstack.instance;

import java.util.concurrent.ConcurrentHashMap;

public class AttributeMap extends ConcurrentHashMap<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final AttributeMap instance = new AttributeMap();
	
	public static AttributeMap getInstance() {
		return instance;
	}

	private AttributeMap() {
		super();
	}
	
	public void put(int subOrderId, String name, String value) {
		if (name != null && value != null) {
			String key = Integer.toString(subOrderId) + "." + name;
			this.put(key, value);
		}
	}
	
	public String get(int subOrderId, String name) {
		String value = null;
		if (name != null) {
			String key = Integer.toString(subOrderId) + "." + name;
			if (this.containsKey(key)) {
				value = this.get(key);
			}
		}
		return value;
	}
	
}
