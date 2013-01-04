package com.inforstack.openstack.security.resource;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inforstack.openstack.security.permission.Permission;



@Entity
public class Resource {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String url;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="permission_id")
	private Permission permission;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
}
