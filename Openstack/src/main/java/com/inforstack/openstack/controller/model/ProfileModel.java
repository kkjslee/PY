package com.inforstack.openstack.controller.model;


public class ProfileModel {

	private Integer itemId;
	
	private Integer cpuId;
	
	private Integer memoryId;
	
	private Integer diskId;
	
	private Integer networkId;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getCpuId() {
		return cpuId;
	}

	public void setCpuId(Integer cpuId) {
		this.cpuId = cpuId;
	}

	public Integer getMemoryId() {
		return memoryId;
	}

	public void setMemoryId(Integer memoryId) {
		this.memoryId = memoryId;
	}

	public Integer getDiskId() {
		return diskId;
	}

	public void setDiskId(Integer diskId) {
		this.diskId = diskId;
	}

	public Integer getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Integer networkId) {
		this.networkId = networkId;
	}
	
}
