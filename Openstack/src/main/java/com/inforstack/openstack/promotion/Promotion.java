package com.inforstack.openstack.promotion;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@JoinColumn(name="display_name")
	private I18nLink displayName;

	private double discount;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
	@Column(name="role_id")
	private Integer roleId;
	
	private boolean deleted;
	
	@Column(name="create_time")
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
