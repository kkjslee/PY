package com.inforstack.openstack.virt.domain.usage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.virt.domain.usage.dd.DiskUsage;
import com.inforstack.openstack.virt.domain.usage.di.InterfaceUsage;

@Entity
@Table(name="domain_usage")
public class DomainUsage {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="instance_id")
	private Integer instanceId;
	
	@Column(name="instance_uuid")
	private String instanceUuid;
	
	private String name;
	
	private Integer state;
	
	@Column(name="max_memory")
	private Long maxMemory;
	
	@Column(name="memory_used")
	private Long memoryUsed;
	
	@Column(name="cpu_number")
	private Integer cpuNumber;
	
	@Column(name="cpu_time")
	private Long cpuTime;
	
	@Column(name="log_time")
	private Date logTime;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="domainUsage")
	@JsonProperty
	private List<DiskUsage> diskUsages = new ArrayList<DiskUsage>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="domainUsage")
	@JsonProperty
	private List<InterfaceUsage> interfaceUsages = new ArrayList<InterfaceUsage>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceUuid() {
		return instanceUuid;
	}

	public void setInstanceUuid(String instanceUuid) {
		this.instanceUuid = instanceUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(Long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public Long getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(Long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public Long getCpuTime() {
		return cpuTime;
	}

	public void setCpuTime(Long cpuTime) {
		this.cpuTime = cpuTime;
	}

	public List<DiskUsage> getDiskUsages() {
		return diskUsages;
	}

	public void setDiskUsages(List<DiskUsage> diskUsages) {
		this.diskUsages = diskUsages;
	}

	public List<InterfaceUsage> getInterfaceUsages() {
		return interfaceUsages;
	}

	public void setInterfaceUsages(List<InterfaceUsage> interfaceUsages) {
		this.interfaceUsages = interfaceUsages;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
}
