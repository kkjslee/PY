package com.inforstack.openstack.api.token;

public class Service {
	
	private String name;
	
	private String type;
	
	private EndPoint[] endpoints;
	
	private String[] endpoints_links;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EndPoint[] getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(EndPoint[] endpoints) {
		this.endpoints = endpoints;
	}

	public String[] getEndpoints_links() {
		return endpoints_links;
	}

	public void setEndpoints_links(String[] endpoints_links) {
		this.endpoints_links = endpoints_links;
	}

}
