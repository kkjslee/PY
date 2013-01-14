package com.inforstack.openstack.tenant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.inforstack.openstack.user.User;

@Entity
public class Tenant {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String dipalyName;
	
	private String email;
	
	private String phone;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String address;
	
	private String postcode;
	
	private String uuid;

	@Column(name="role_id")
	private int roleId;
	
	@Transient
	private com.inforstack.openstack.api.keystone.Tenant openstatckTenant;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tenant_user", 
		joinColumns = { @JoinColumn(name = "tenant_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "user_id") }
	)
	private List<User> users = new ArrayList<User>();
	
	private int ageing;
	
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

	public String getDipalyName() {
		return dipalyName;
	}

	public void setDipalyName(String dipalyName) {
		this.dipalyName = dipalyName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getAgeing() {
		return ageing;
	}

	public void setAgeing(int ageing) {
		this.ageing = ageing;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public com.inforstack.openstack.api.keystone.Tenant getOpenstatckTenant() {
		return openstatckTenant;
	}

	public void setOpenstatckTenant(
			com.inforstack.openstack.api.keystone.Tenant openstatckTenant) {
		this.openstatckTenant = openstatckTenant;
	}
}
