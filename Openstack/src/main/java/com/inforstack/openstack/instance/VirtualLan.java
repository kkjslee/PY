package com.inforstack.openstack.instance;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inforstack.openstack.tenant.Tenant;

@Entity
public class VirtualLan {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	private String name;
	
	private boolean first;
	
	private String cidr;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;

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

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public String getCidr() {
		return cidr;
	}

	public void setCidr(String cidr) {
		this.cidr = cidr;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

}
