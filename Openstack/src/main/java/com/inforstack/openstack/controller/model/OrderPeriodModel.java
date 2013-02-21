package com.inforstack.openstack.controller.model;

import java.util.Date;

public class OrderPeriodModel {

	private Integer id;
	
	private String name;
	
	private Integer periodType;
	
	private Integer periodQuantity;

	private Date createTime;
	
	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPeriodType() {
		return periodType;
	}

	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}

	public Integer getPeriodQuantity() {
		return periodQuantity;
	}

	public void setPeriodQuantity(Integer periodQuantity) {
		this.periodQuantity = periodQuantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
}
