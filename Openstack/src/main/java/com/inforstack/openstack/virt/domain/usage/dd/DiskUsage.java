package com.inforstack.openstack.virt.domain.usage.dd;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inforstack.openstack.virt.domain.usage.DomainUsage;

@Entity
public class DiskUsage {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Column(name="capacity_bytes")
	private Long capacityBytes;
	
	@Column(name="allocation_bytes")
	private Long allocationBytes;
	
	@Column(name="physical_bytes")
	private Long physicalBytes;
	
	@Column(name="read_number")
	private Integer readNumber;
	
	@Column(name="read_bytes")
	private Long readBytes;
	
	@Column(name="write_number")
	private Integer writeNumber;
	
	@Column(name="write_bytes")
	private Long writeBytes;
	
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

	public Long getCapacityBytes() {
		return capacityBytes;
	}

	public void setCapacityBytes(Long capacityBytes) {
		this.capacityBytes = capacityBytes;
	}

	public Long getAllocationBytes() {
		return allocationBytes;
	}

	public void setAllocationBytes(Long allocationBytes) {
		this.allocationBytes = allocationBytes;
	}

	public Long getPhysicalBytes() {
		return physicalBytes;
	}

	public void setPhysicalBytes(Long physicalBytes) {
		this.physicalBytes = physicalBytes;
	}

	public Integer getReadNumber() {
		return readNumber;
	}

	public void setReadNumber(Integer readNumber) {
		this.readNumber = readNumber;
	}

	public Long getReadBytes() {
		return readBytes;
	}

	public void setReadBytes(Long readBytes) {
		this.readBytes = readBytes;
	}

	public Integer getWriteNumber() {
		return writeNumber;
	}

	public void setWriteNumber(Integer writeNumber) {
		this.writeNumber = writeNumber;
	}

	public Long getWriteBytes() {
		return writeBytes;
	}

	public void setWriteBytes(Long writeBytes) {
		this.writeBytes = writeBytes;
	}

	public DomainUsage getDomainUsage() {
		return domainUsage;
	}

	public void setDomainUsage(DomainUsage domainUsage) {
		this.domainUsage = domainUsage;
	}
	
}
