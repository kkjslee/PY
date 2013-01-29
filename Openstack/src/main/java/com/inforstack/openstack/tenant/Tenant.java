package com.inforstack.openstack.tenant;

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
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.promotion.Promotion;
import com.inforstack.openstack.user.User;

@Entity
public class Tenant {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@Column(name="display_name")
	private String dipalyName;
	
	private String email;
	
	private String phone;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String address;
	
	private String postcode;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn
	private I18nLink description;
	
	private String uuid;

	@Column(name="role_id")
	private int roleId;
	
	@Column(name="agent_id")
	private Integer agentId;
	
	@Column(name="creator_id", insertable=false, updatable=false)
	private Integer creatorId;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="creator_id")
	private User creator;
	
	@Column(name="promotion_id", insertable=false, updatable=false)
	private Integer promotionId;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="promotion_id")
	private Promotion promotion;
	
	@Transient
	private com.inforstack.openstack.api.keystone.Tenant openstatckTenant;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "tenant_user", 
		joinColumns = { @JoinColumn(name = "tenant_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "user_id") }
	)
	private List<User> users = new ArrayList<User>();
	
	private int ageing;
	
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

	/**
	 * transient
	 * @return
	 */
	public Integer getCreatorId() {
		return creatorId;
	}
	
	/**
	 * transient
	 * @param creatorId
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	/**
	 * transient
	 * @return
	 */
	public Integer getPromotionId() {
		return promotionId;
	}

	/**
	 * transient
	 * @param promotionId
	 */
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public I18nLink getDescription() {
		return description;
	}

	public void setDescription(I18nLink description) {
		this.description = description;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public com.inforstack.openstack.api.keystone.Tenant getOpenstatckTenant() {
		return openstatckTenant;
	}

	public void setOpenstatckTenant(
			com.inforstack.openstack.api.keystone.Tenant openstatckTenant) {
		this.openstatckTenant = openstatckTenant;
	}
}
