package com.inforstack.openstack.security.group;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.inforstack.openstack.i18n.I18nLink;
import com.inforstack.openstack.security.permission.Permission;
import com.inforstack.openstack.tenant.Tenant;



@Entity
public class SecurityGroup {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private I18nLink description;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
	private boolean valid;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "permission_group", 
		joinColumns = { @JoinColumn(name = "group_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "permmision_id", insertable=false, updatable = false) }
	)
	private List<Permission> permissions;
	
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
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public I18nLink getDescription() {
		return description;
	}
	public void setDescription(I18nLink description) {
		this.description = description;
	}
}
