package com.inforstack.openstack.item;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="profile")
public class Profile {

	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="cpu_id")
	private ItemSpecification cpu;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="memory_id")
	private ItemSpecification memory;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="disk_id")
	private ItemSpecification disk;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="network_id")
	private ItemSpecification network;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ItemSpecification getCpu() {
		return cpu;
	}

	public void setCpu(ItemSpecification cpu) {
		this.cpu = cpu;
	}

	public ItemSpecification getMemory() {
		return memory;
	}

	public void setMemory(ItemSpecification memory) {
		this.memory = memory;
	}

	public ItemSpecification getDisk() {
		return disk;
	}

	public void setDisk(ItemSpecification disk) {
		this.disk = disk;
	}

	public ItemSpecification getNetwork() {
		return network;
	}

	public void setNetwork(ItemSpecification network) {
		this.network = network;
	}
	
}
