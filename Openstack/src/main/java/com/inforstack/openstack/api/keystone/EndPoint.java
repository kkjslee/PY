package com.inforstack.openstack.api.keystone;

public class EndPoint {
	
	private String id;
	
	private String region;
	
	private String adminURL;
	
	private String internalURL;
	
	private String publicURL;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAdminURL() {
		return adminURL;
	}

	public void setAdminURL(String adminURL) {
		this.adminURL = adminURL;
	}

	public String getInternalURL() {
		return internalURL;
	}

	public void setInternalURL(String internalURL) {
		this.internalURL = internalURL;
	}

	public String getPublicURL() {
		return publicURL;
	}

	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

}
