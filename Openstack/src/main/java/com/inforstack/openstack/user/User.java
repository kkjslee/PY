package com.inforstack.openstack.user;

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

import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.security.role.Role;
import com.inforstack.openstack.tenant.Tenant;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private String mobile;
	
	private String email;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String address;
	
	private String postcode;
	
	private String question;
	
	private String answer;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="role_id")
	private Role role;
	
	private Integer defaultTenantId;
	
	private Integer status;
	
	private Integer ageing;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tanent_user", 
		joinColumns = { @JoinColumn(name = "user_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "tenant_id", insertable=false, updatable = false) }
	)
	private List<Tenant> tanents;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_securitygroup", 
		joinColumns = { @JoinColumn(name = "user_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "group_id", insertable=false, updatable = false) }
	)
	private List<SecurityGroup> securityGroups;
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAgeing() {
		return ageing;
	}

	public void setAgeing(Integer ageing) {
		this.ageing = ageing;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Tenant> getTanents() {
		return tanents;
	}

	public void setTanents(List<Tenant> tanents) {
		this.tanents = tanents;
	}

	public List<SecurityGroup> getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(List<SecurityGroup> securityGroups) {
		this.securityGroups = securityGroups;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getDefaultTenantId() {
		return defaultTenantId;
	}

	public void setDefaultTenantId(Integer defaultTenantId) {
		this.defaultTenantId = defaultTenantId;
	}
	
}
