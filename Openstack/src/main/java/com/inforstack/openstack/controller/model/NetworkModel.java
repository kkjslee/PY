package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.inforstack.openstack.utils.OpenstackUtil;

public class NetworkModel {

	private String id;

	@Size(min = 6, max = 45, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_-]+$", message = "{not.valid}")
	private String name;

	private String tenant;

	private boolean adminStateUp;

	private boolean shared;

	private boolean external;

	private String[] subnets;

	private String[] subnetNamesWithNetwork;

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

	public String getAdminStateUpDisplay() {
		String adminStateUpDisplay = "";
		if (adminStateUp == false) {
			adminStateUpDisplay = OpenstackUtil
					.getMessage("adminstateup.not.label");
		} else {
			adminStateUpDisplay = OpenstackUtil
					.getMessage("adminstateup.yes.label");
		}
		return adminStateUpDisplay;
	}

	public String getExternalDisplay() {
		String externalDisplay = "";
		if (external == false) {
			externalDisplay = OpenstackUtil.getMessage("external.not.label");
		} else {
			externalDisplay = OpenstackUtil.getMessage("external.yes.label");
		}
		return externalDisplay;
	}

	public void setShareDisplay(String shareDisplay) {
		this.shareDisplay = shareDisplay;
	}

	public String getSubnetNameString() {
		subnetNameString = "";
		if (subnetNamesWithNetwork != null && subnetNamesWithNetwork.length > 0) {
			int length = subnetNamesWithNetwork.length;
			for (int i = 0; i < length; i++) {
				subnetNameString = subnetNameString + subnetNamesWithNetwork[i];
				if (i < length - 1) {
					subnetNameString = subnetNameString + ",";
				}
			}
		}
		return subnetNameString;
	}

	public String[] getSubnetNamesWithNetwork() {
		return subnetNamesWithNetwork;
	}

	public void setSubnetNamesWithNetwork(String[] subnetNamesWithNetwork) {
		this.subnetNamesWithNetwork = subnetNamesWithNetwork;
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

	public boolean getAdminStateUp() {
		return adminStateUp;
	}

	public void setAdminStateUp(boolean adminStateUp) {
		this.adminStateUp = adminStateUp;
	}

	public boolean getShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public boolean getExternal() {
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
