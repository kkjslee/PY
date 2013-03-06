package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Router {
	
	public static final class Gateway {
		
		@JsonProperty("network_id")
		private String network;

		public String getNetwork() {
			return network;
		}

		public void setNetwork(String network) {
			this.network = network;
		}
		
	}
	
	private String id;
	
	private String name;
	
	@JsonProperty("tenant_id")
	private String tenant;
	
	@JsonProperty("admin_state_up")
	private boolean adminStateUp;
	
	private String status;
	
	@JsonProperty("external_gateway_info")
	private Gateway gateway;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}
