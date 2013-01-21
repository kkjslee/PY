package com.inforstack.openstack.promotion;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.tenant.Tenant;

@Entity
public class Promotion {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private I18nLink displayName;

	private double discount;
	
	private Tenant tenant;
	
	private boolean deleted;
	
	private Date createTime;

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

	public I18nLink getDisplayName() {
		return displayName;
	}

	public void setDisplayName(I18nLink displayName) {
		this.displayName = displayName;
	}

	public double getDiscount() {
		return discount;
	}
	
	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
