package com.inforstack.openstack.controller.model;

/**
 * this is for json response
 * 
 * @author shaw
 * 
 */
public class FlavorModel {

  private String flavorId;

  private String flavorName;

  private int vcpus;

  private int ram;

  private int disk;

  public FlavorModel() {
    super();
    // TODO Auto-generated constructor stub
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

  public int getVcpus() {
    return vcpus;
  }

  public void setVcpus(int vcpus) {
    this.vcpus = vcpus;
  }

  public int getRam() {
    return ram;
  }

  public void setRam(int ram) {
    this.ram = ram;
  }

  public int getDisk() {
    return disk;
  }

  public void setDisk(int disk) {
    this.disk = disk;
  }

}
