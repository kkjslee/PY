package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FloatingIP {
	
	private String id;
	
	@JsonProperty("tenant_id")
	private String tenant;

	@JsonProperty("floating_ip_address")
	private String floatingIP;
	
	@JsonProperty("floating_network_id")
	private String floatingNetwork;
	
	@JsonProperty("fixed_ip_address")
	private String fixedIp;
	
	@JsonProperty("router_id")
	private String router;
	
	@JsonProperty("port_id")
	private String port;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getFloatingIP() {
		return floatingIP;
	}

	public void setFloatingIP(String floatingIP) {
		this.floatingIP = floatingIP;
	}

	public String getFloatingNetwork() {
		return floatingNetwork;
	}

	public void setFloatingNetwork(String floatingNetwork) {
		this.floatingNetwork = floatingNetwork;
	}

	public String getFixedIp() {
		return fixedIp;
	}

	public void setFixedIp(String fixedIp) {
		this.fixedIp = fixedIp;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
