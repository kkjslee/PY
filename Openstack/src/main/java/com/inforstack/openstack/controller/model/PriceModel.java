package com.inforstack.openstack.controller.model;

import java.util.Date;

public class PriceModel {

	private Integer itemSpecificationId;
	
	private Float value;
	
	private Date activated;
	
	public PriceModel() {
		
	}

	public Integer getItemSpecificationId() {
		return itemSpecificationId;
	}

	public void setItemSpecificationId(Integer itemSpecificationId) {
		this.itemSpecificationId = itemSpecificationId;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}
	
}
