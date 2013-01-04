package com.inforstack.openstack.tenant;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.inforstack.openstack.user.User;


@Entity
public class Tenant {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String dipalyName;

	@Column(name="role_id")
	private Integer roleId;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tanent_user", 
		joinColumns = { @JoinColumn(name = "tanent_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "user_id", insertable=false, updatable = false) }
	)
	private List<User> users;
	
	private boolean deleted;
	
	private Date createTime;
	
	private Date updateTime;

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

	public String getDipalyName() {
		return dipalyName;
	}

	public void setDipalyName(String dipalyName) {
		this.dipalyName = dipalyName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isDeleted() {
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
