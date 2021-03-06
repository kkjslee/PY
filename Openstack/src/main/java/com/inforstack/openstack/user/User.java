package com.inforstack.openstack.user;

import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.SecurityUtils;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="user_name")
	private String username;
	
	private String password;
	
	@Column(name="first_name")
	private String firstname;
	
	@Column(name="last_name")
	private String lastname;
	
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
	
	@Column(name="default_language")
	private Integer defaultLanguage;

	@Column(name="role_id")
	private int roleId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="default_tenant_id")
	private Tenant defaultTenant;
	
	private Integer status;
	
	private Integer ageing;
	
	private String uuid;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tenant_user", 
		joinColumns = { @JoinColumn(name = "user_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "tenant_id") }
	)
	private List<Tenant> tanents = new ArrayList<Tenant>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_security_group", 
		joinColumns = { @JoinColumn(name = "user_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "group_id", insertable=false, updatable = false) }
	)
	private List<SecurityGroup> securityGroups = new ArrayList<SecurityGroup>();
	
	@Column(name="create_time")
	private Date createTime;
	
	private Integer tried;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="user_instance",
		joinColumns = { @JoinColumn(name="user_id", insertable=false, updatable = false) },
		inverseJoinColumns = { @JoinColumn(name= "instance_id", insertable=false, updatable = false) }
	)
	private List<Instance> instances = new ArrayList<Instance>();
	
	@Transient
	private com.inforstack.openstack.api.keystone.User openstackUser;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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
	
	public Tenant getDefaultTenant() {
		return defaultTenant;
	}

	public void setDefaultTenant(Tenant defaultTenant) {
		this.defaultTenant = defaultTenant;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Integer defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
	
	public Integer getTried() {
		return tried;
	}

	public void setTried(Integer tried) {
		this.tried = tried;
	}

	public com.inforstack.openstack.api.keystone.User getOpenstackUser() {
		if( openstackUser == null ){
			com.inforstack.openstack.api.keystone.User ou =  new com.inforstack.openstack.api.keystone.User();
			ou.setEmail(email);
			ou.setEnabled(SecurityUtils.isUserEnabled(this));
			ou.setId(uuid);
			ou.setName(username);
			ou.setPassword(password);
			
			return ou;
		}else{
			return openstackUser;
		}
	}

	/**
	 * Transient
	 * @param openstackUser
	 */
	public void setOpenstackUser(com.inforstack.openstack.api.keystone.User openstackUser) {
		this.openstackUser = openstackUser;
	}

}
