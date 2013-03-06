package com.inforstack.openstack.network;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.tenant.Tenant;

@Entity
public class Subnet {

	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	private String name;
	
	private int a1;
	
	private int a2;
	
	private int a3;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=true)
	@JoinColumn(name="network_id")
	private Network network;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=true)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="dataCenter_id")
	private DataCenter dataCenter;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getA1() {
		return a1;
	}

	public void setA1(int a1) {
		this.a1 = a1;
	}

	public int getA2() {
		return a2;
	}

	public void setA2(int a2) {
		this.a2 = a2;
	}

	public int getA3() {
		return a3;
	}

	public void setA3(int a3) {
		this.a3 = a3;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public DataCenter getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(DataCenter dataCenter) {
		this.dataCenter = dataCenter;
	}
	
}
