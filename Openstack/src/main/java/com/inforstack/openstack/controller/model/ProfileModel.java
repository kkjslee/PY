package com.inforstack.openstack.controller.model;


public class ProfileModel {

	private Integer item;
	
	private Integer cpu;
	
	private Integer memory;
	
	private Integer disk;
	
	private Integer network;
	
	public ProfileModel() {
		
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}

	public Integer getNetwork() {
		return network;
	}

	public void setNetwork(Integer network) {
		this.network = network;
	}
	
}
