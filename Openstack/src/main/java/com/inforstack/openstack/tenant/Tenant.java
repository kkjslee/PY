package com.inforstack.openstack.tenant;

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

import com.inforstack.openstack.security.role.Role;
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

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="role_id")
	private Role role;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tanent_user", 
		joinColumns = { @JoinColumn(name = "tanent_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "user_id", insertable=false, updatable = false) }
	)
	private List<User> users;
	
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
	
}
