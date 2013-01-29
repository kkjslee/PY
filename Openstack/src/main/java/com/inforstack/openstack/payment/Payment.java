package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inforstack.openstack.tenant.Tenant;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private BigDecimal amount;
	
	private BigDecimal balance;
	
	private int type;
	
	private int status;
	
	@Column(name="tenant_id", insertable=false, updatable=false)
	private Integer tenantId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
	@Column(name="create_time")
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * transient
	 * @return
	 */
	public Tenant getTenant() {
		return tenant;
	}

	/**
	 * transient
	 * @param tenant
	 */
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
