package com.inforstack.openstack.controller.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Range;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;

/**
 * vm instance model used for response
 * 
 * @author shaw
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InstanceModel {
	private static final Logger log = new Logger(InstanceModel.class);
	// vm uuid
	private String vmid;

	@Size(min = Constants.DEFAULT_NAME_MIN_LENGTH, max = Constants.DEFAULT_NAME_MAX_LENGTH, message = "{size.not.valid}")
	@Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "{not.valid}")
	private String vmname;
	
	// cpu number
	@Range(min = Constants.DEFAULT_CPU_NUM_MIN_LIMIT, max = Constants.DEFAULT_CPU_NUM_MAX_LIMIT, message = "{size.not.valid}")
	private Integer cpus;
	// memory size,DEFAULT UNIT MB
	@Range(min = Constants.DEFAULT_RAM_SIZE_MIN_LIMIT, max = Constants.DEFAULT_RAM_SIZE_MAX_LIMIT, message = "{size.not.valid}")
	private Integer memory;
	@Range(min = Constants.DEFAULT_RAM_SIZE_MIN_LIMIT, max = Constants.DEFAULT_RAM_SIZE_MAX_LIMIT, message = "{size.not.valid}")
	private Integer maxmemory;

	@Range(min = Constants.DEFAULT_CPU_NUM_MIN_LIMIT, max = Constants.DEFAULT_CPU_NUM_MAX_LIMIT, message = "{size.not.valid}")
	private Integer maxcpus;

	@Range(min = Constants.DEFAULT_DISK_SIZE_MIN_LIMIT, max = Constants.DEFAULT_DISK_SIZE_MAX_LIMIT, message = "{size.not.valid}")
	private Integer disksize;
	// vm create time
	private Date starttime;
	// vm expire time
	private Date expiretime;
	private Date updatetime;
	// public up bound
	private List<String> publicips;
	private List<String> privateips;

	// vm status
	private String status;
	// vm status display name
	private String statusdisplay;
	// vm vnc access address
	private String accesspoint;
	private String tenantId;
	// zone
	private String zone;
	private String zonedisplay;
	// vm os type
	private String ostype;
	private String assignedto;

	// task staus
	private String taskStatus;

	private String imageId;

	private String flavorId;
	private String flavorName;
	
	private boolean isProcessing;
	private int powerOn;
	// status+task+power
	private String statusString;

	private Map<String, String> addresses;
	
	private String period;
	
	private String region;
	
	private AttachmentModel attachmentModel;

	public InstanceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getVmid() {
		return vmid;
	}

	public void setVmid(String vmid) {
		this.vmid = vmid;
	}

	public String getVmname() {
		return vmname;
	}

	public void setVmname(String vmname) {
		this.vmname = vmname;
	}

	public Integer getCpus() {
		return cpus;
	}

	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(Date expiretime) {
		this.expiretime = expiretime;
	}

	public List<String> getPublicips() {
		return publicips;
	}

	public void setPublicips(List<String> publicips) {
		this.publicips = publicips;
	}

	public List<String> getPrivateips() {
		return privateips;
	}

	public void setPrivateips(List<String> privateips) {
		this.privateips = privateips;
	}

	public Integer getDisksize() {
		return disksize;
	}

	public void setDisksize(Integer disksize) {
		this.disksize = disksize;
	}

	public Integer getMaxmemory() {
		return maxmemory;
	}

	public void setMaxmemory(Integer maxmemory) {
		this.maxmemory = maxmemory;
	}

	public Integer getMaxcpus() {
		return maxcpus;
	}

	public void setMaxcpus(Integer maxcpus) {
		this.maxcpus = maxcpus;
	}

	public String getStatusdisplay() {
		return statusdisplay;
	}

	public void setStatusdisplay(String statusdisplay) {
		this.statusdisplay = statusdisplay;
	}

	public String getAccesspoint() {
		return accesspoint;
	}

	public void setAccesspoint(String accesspoint) {
		this.accesspoint = accesspoint;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getZonedisplay() {
		return zonedisplay;
	}

	public void setZonedisplay(String zonedisplay) {
		this.zonedisplay = zonedisplay;
	}

	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public String getAssignedto() {
		return assignedto;
	}

	public void setAssignedto(String assignedto) {
		this.assignedto = assignedto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updateTime) {
		this.updatetime = updateTime;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public String getFlavorName() {
		return flavorName;
	}

	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}

	public boolean getIsProcessing() {
		String powerString = (powerOn == 1 ? "on" : "off");
		this.statusString = StringUtil.joinStringWithEmpty(status, taskStatus,
				powerString + "").toLowerCase();
		log.info("vm :" + vmid + " vmname: " + vmname
				+ " current status string : " + statusString);
		if (Constants.VM_STATUS_DONE_STRING.indexOf("|" + statusString + "|") == -1) {
			return true;
		} else {
			return false;
		}
	}

	public void setIsProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}

	public int getPowerOn() {
		return powerOn;
	}

	public void setPowerOn(int powerOn) {
		this.powerOn = powerOn;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public Map<String, String> getAddresses() {
		return addresses;
	}

	public String getAddressString() {
		Iterator<Entry<String, String>> it = addresses.entrySet().iterator();
		String ipString = "";
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String values = entry.getValue();
			ipString = "[" + key + ":" + values + "]";
		}
		return ipString;
	}

	public void setAddresses(Map<String, String> addresses) {
		this.addresses = addresses;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public AttachmentModel getAttachmentModel() {
		return attachmentModel;
	}

	public void setAttachmentModel(AttachmentModel attachmentModel) {
		this.attachmentModel = attachmentModel;
	}

}
