package com.inforstack.openstack.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Network {
	
	@Id
	@GeneratedValue
	private int id;
	
	private boolean web;

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}

}
