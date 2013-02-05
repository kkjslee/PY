package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Network {
	
	private String id;
	
	private String name;
	
	@JsonProperty("tenant_id")
	private String tenant;
	
	@JsonProperty("admin_state_up")
	private boolean adminStateUp;
	
	private boolean shared;
	
	@JsonProperty("router:external")
	private boolean external;
	
	private String[] subnets;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public boolean isAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String[] getSubnets() {
		return subnets;
	}

	public void setSubnets(String[] subnets) {
		this.subnets = subnets;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
