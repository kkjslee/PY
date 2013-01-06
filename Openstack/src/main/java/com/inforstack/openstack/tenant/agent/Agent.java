package com.inforstack.openstack.tenant.agent;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;



import org.hibernate.annotations.GenericGenerator;

import com.inforstack.openstack.tenant.Tenant;


@Entity
public class Agent {
	
	@Id
	@GeneratedValue(generator="agent-uuid")
	@GenericGenerator(name="agent-uuid", strategy = "uuid")
	private String uuid;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="parent")
	private Agent parentAgent;
	
	private double commission;
	
	@Column(name="tenant_id", insertable=false, updatable=false)
	private Integer tenantId;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="tenant_id")
	private Tenant tenant;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Agent getParentAgent() {
		return parentAgent;
	}
	public void setParentAgent(Agent parentAgent) {
		this.parentAgent = parentAgent;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
}
