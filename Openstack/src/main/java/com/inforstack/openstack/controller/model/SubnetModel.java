package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.inforstack.openstack.api.quantum.Subnet.AllocationPool;

public class SubnetModel {

	private String id;

	@Size(min = 6, max = 45, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "{not.valid}")
	private String name;

	private String tenant;

	private String network;

	private int ipVersion;

	private String gateway;

	private String cidr;

	private boolean dhcp;

	private AllocationPool[] pools;

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

	public AllocationPool[] getPools() {
		return pools;
	}

	public void setPools(AllocationPool[] pools) {
		this.pools = pools;
	}

}
