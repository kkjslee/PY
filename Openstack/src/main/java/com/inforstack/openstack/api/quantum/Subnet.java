package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Subnet {
	
	private String id;
	
	private String name;
	
	@JsonProperty("tenant_id")
	private String tenant;
	
	@JsonProperty("network_id")
	private String network;
	
	@JsonProperty("ip_version")
	private int ipVersion;
	
	@JsonProperty("gateway_ip")
	private String gateway;
	
	private String cidr;
	
	@JsonProperty("enable_dhcp")
	private boolean dhcp;

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

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public int getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(int ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public boolean isDhcp() {
		return dhcp;
	}

	public void setDhcp(boolean dhcp) {
		this.dhcp = dhcp;
	}

}
