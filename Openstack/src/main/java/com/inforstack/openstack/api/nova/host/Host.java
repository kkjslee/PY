package com.inforstack.openstack.api.nova.host;

import org.codehaus.jackson.annotate.JsonProperty;


public class Host {
	
	private String zone;
	
	@JsonProperty("host_name")
	private String name;
	
	private String service;

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
