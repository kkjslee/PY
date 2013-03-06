package com.inforstack.openstack.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VolumeType {

	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name="data_center_id")
	private DataCenter dataCenter;
	
	@Column(name="ref_id")
	private String refId;
	
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

	public DataCenter getDataCenter() {
		return dataCenter;
	}

	public void setDataCenter(DataCenter dataCenter) {
		this.dataCenter = dataCenter;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	
}
