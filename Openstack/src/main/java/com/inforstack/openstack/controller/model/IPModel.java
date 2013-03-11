package com.inforstack.openstack.controller.model;

import java.util.Date;

import com.inforstack.openstack.utils.OpenstackUtil;

public class IPModel {

	private String id;

	private Integer subOrderId;

	private String zone;
	
	private String address;
	
	private String vmId;
	
	private String vmName;

	private Date created;

	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusDisplay() {
		if (status != null) {
			return OpenstackUtil.getMessage(status + ".label");
		} else {
			return null;
		}
	}
	
}
