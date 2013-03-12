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
@Table(name="network_type")
public class NetworkType {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	private boolean web;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name="data_center_id")
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

	public boolean isWeb() {
		return web;
	}

	public void setWeb(boolean web) {
		this.web = web;
	}
	
	public DataCenter getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(DataCenter dataCenter) {
		this.dataCenter = dataCenter;
	}

}
