package com.inforstack.openstack.api.host;


public class Host {
	
	private String zone;
	
	private String host_name;
	
	private String service;

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
