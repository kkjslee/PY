package com.inforstack.openstack.user;

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

import com.inforstack.openstack.security.group.SecurityGroup;
import com.inforstack.openstack.tenant.Tenant;



@Entity
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	private String password;
	
	private String realName;
	
	private String phone;
	
	private String email;
	
	private String address;
	
	private String question;
	
	private String answer;
	
	@Column(name="role_id")
	private Integer roleId;
	
	private boolean valid;
	
	private boolean deleted;
	
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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
	
}
