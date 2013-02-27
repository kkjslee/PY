package com.inforstack.openstack.api.quantum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Port {
	
	public static final class IP {
		
		@JsonProperty("ip_address")
		private String ip;
		
		@JsonProperty("subnet_id")
		private String subnet;

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getSubnet() {
			return subnet;
		}

		public void setSubnet(String subnet) {
			this.subnet = subnet;
		}
		
	}

	private String id;
	
	private String name;
	
	@JsonProperty("tenant_id")
	private String tenant;
	
	@JsonProperty("network_id")
	private String network;
	
	@JsonProperty("device_id")
	private String device;
	
	@JsonProperty("mac_address")
	private String mac;
	
	@JsonProperty("fixed_ips")
	private IP[] ips;
	
	@JsonProperty("admin_state_up")
	private boolean adminStateUp;
	
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

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public IP[] getIps() {
		return ips;
	}

	public void setIps(IP[] ips) {
		this.ips = ips;
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
	
}
