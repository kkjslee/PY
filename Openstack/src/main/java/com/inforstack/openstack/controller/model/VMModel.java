package com.inforstack.openstack.controller.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * vm instance model used for response
 * 
 * @author shaw
 * 
 */
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class VMModel {
  // vm uuid
  private String vmid;
  private String vmname;
  // cpu number
  private Integer cpus;
  // memory size
  private Integer memory;
  // vm create time
  private Date starttime;
  // vm expire time
  private Date expiretime;
  private Date updatetime;
  // public up bound
  private List<String> publicips;
  private List<String> privateips;
  private Integer disksize;
  private Integer maxmemory;
  private Integer maxcpu;
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
  // sub user specified
  private String assignedto;

  public VMModel() {
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

  public Integer getMaxcpu() {
    return maxcpu;
  }

  public void setMaxcpu(Integer maxcpu) {
    this.maxcpu = maxcpu;
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

}
