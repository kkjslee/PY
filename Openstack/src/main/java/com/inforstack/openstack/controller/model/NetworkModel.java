package com.inforstack.openstack.controller.model;

import com.inforstack.openstack.utils.OpenstackUtil;

public class NetworkModel {

	private String id;

	private String name;

	private String tenant;

	private boolean adminStateUp;

	private boolean shared;

	private boolean external;

	private String[] subnets;

	private String[] subnetNames;

	private String status;

	private String subnetNameString;

	private String shareDisplay;

	public String getShareDisplay() {
		shareDisplay = "";
		if (shared == false) {
			shareDisplay = OpenstackUtil.getMessage("shared.not.label");
		} else {
			shareDisplay = OpenstackUtil.getMessage("shared.yes.label");
		}
		return shareDisplay;
	}

	public void setShareDisplay(String shareDisplay) {
		this.shareDisplay = shareDisplay;
	}

	public String getSubnetNameString() {
		subnetNameString = "";
		if (subnetNames != null && subnetNames.length > 0) {
			int length = subnetNames.length;
			for (int i = 0; i < length; i++) {
				subnetNameString = subnetNameString + subnetNames[i];
				if (i < length - 1) {
					subnetNameString = subnetNameString + ",";
				}
			}
		}
		return subnetNameString;
	}

	public String[] getSubnetNames() {
		return subnetNames;
	}

	public void setSubnetNames(String[] subnetNames) {
		this.subnetNames = subnetNames;
	}

	public void setSubnetNameString(String subnetNameString) {
		this.subnetNameString = subnetNameString;
	}

	public NetworkModel() {
		super();
		// TODO Auto-generated constructor stub
	}

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
