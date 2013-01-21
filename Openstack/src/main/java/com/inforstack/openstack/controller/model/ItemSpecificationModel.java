package com.inforstack.openstack.controller.model;

import java.util.Date;

public class ItemSpecificationModel {
	
	private Integer id;
	
	private I18nModel[] name;
	
	private float defaultPrice;
	
	private Integer osType;
	
	private String refId;
	
	private Boolean available;
	
	private Date created;
	
	private Date updated;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public I18nModel[] getName() {
		return name;
	}

	public void setName(I18nModel[] name) {
		this.name = name;
	}

	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Integer getOsType() {
		return osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
