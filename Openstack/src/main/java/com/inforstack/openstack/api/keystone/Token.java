package com.inforstack.openstack.api.keystone;

import java.util.Date;

import com.inforstack.openstack.api.keystone.Tenant;

public class Token {
	
	private String id;
	
	private Date issued_at;
	
	private Date expires;
	
	private Tenant tenant;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getIssued_at() {
		return issued_at;
	}

	public void setIssued_at(Date issued_at) {
		this.issued_at = issued_at;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
}
