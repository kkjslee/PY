package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.inforstack.openstack.api.quantum.Port.IP;

public class PortModel {

	private String id;

	@Size(min = 6, max = 45, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$", message = "{not.valid}")
	private String name;

	private String tenant;

	private String network;

	private String deviceId;

	private String deviceName;

	private String deviceOwner;

	private String mac;

	private IP[] ips;

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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public IP[] getIps() {
		return ips;
	}

	public String getIpsDisplay() {
		String ipString = "";
		if (ips != null && ips.length > 0) {
			int length = ips.length;
			for (int i = 0; i < length; i++) {
				ipString = ipString + ips[i].getIp();
				if (i < length - 1) {
					ipString = ipString + ";";
				}
			}
		}
		return ipString;
	}

	public void setIps(IP[] ips) {
		this.ips = ips;
	}

	public boolean getAdminStateUp() {
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceOwner() {
		return deviceOwner;
	}

	public void setDeviceOwner(String deviceOwner) {
		this.deviceOwner = deviceOwner;
	}

}
