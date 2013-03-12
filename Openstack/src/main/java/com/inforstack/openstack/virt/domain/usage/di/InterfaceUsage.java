package com.inforstack.openstack.virt.domain.usage.di;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inforstack.openstack.virt.domain.usage.DomainUsage;

@Entity
@Table(name="interface_usage")
public class InterfaceUsage {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Column(name="incoming_bytes")
	private Long incomingBytes;
	
	@Column(name="incoming_packages")
	private Integer incomingPackages;
	
	@Column(name="incoming_errors")
	private Integer incomingErrors;
	
	@Column(name="incoming_drop")
	private Integer incomingDrop;
	
	@Column(name="outgoing_bytes")
	private Long outgoingBytes;
	
	@Column(name="outgoing_packages")
	private Integer outgoingPackages;
	
	@Column(name="outgoing_errors")
	private Integer outgoingErrors;
	
	@Column(name="outgoing_drop")
	private Integer outgoingDrop;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="domain_usage_id")
	private DomainUsage domainUsage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIncomingBytes() {
		return incomingBytes;
	}

	public void setIncomingBytes(Long incomingBytes) {
		this.incomingBytes = incomingBytes;
	}

	public Integer getIncomingPackages() {
		return incomingPackages;
	}

	public void setIncomingPackages(Integer incomingPackages) {
		this.incomingPackages = incomingPackages;
	}

	public Integer getIncomingErrors() {
		return incomingErrors;
	}

	public void setIncomingErrors(Integer incomingErrors) {
		this.incomingErrors = incomingErrors;
	}

	public Integer getIncomingDrop() {
		return incomingDrop;
	}

	public void setIncomingDrop(Integer incomingDrop) {
		this.incomingDrop = incomingDrop;
	}

	public Long getOutgoingBytes() {
		return outgoingBytes;
	}

	public void setOutgoingBytes(Long outgoingBytes) {
		this.outgoingBytes = outgoingBytes;
	}

	public Integer getOutgoingPackages() {
		return outgoingPackages;
	}

	public void setOutgoingPackages(Integer outgoingPackages) {
		this.outgoingPackages = outgoingPackages;
	}

	public Integer getOutgoingErrors() {
		return outgoingErrors;
	}

	public void setOutgoingErrors(Integer outgoingErrors) {
		this.outgoingErrors = outgoingErrors;
	}

	public Integer getOutgoingDrop() {
		return outgoingDrop;
	}

	public void setOutgoingDrop(Integer outgoingDrop) {
		this.outgoingDrop = outgoingDrop;
	}

	public DomainUsage getDomainUsage() {
		return domainUsage;
	}

	public void setDomainUsage(DomainUsage domainUsage) {
		this.domainUsage = domainUsage;
	}
}
