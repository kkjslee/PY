package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Subnet {

	public static final class AllocationPool {

		private String start;

		private String end;

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

	}

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

	@JsonProperty("allocation_pools")
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

	public boolean getDhcp() {
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
