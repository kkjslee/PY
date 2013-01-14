package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.inforstack.openstack.utils.Constants;

/**
 * this is for json response
 * 
 * @author shaw
 * 
 */
public class FlavorModel {

  private String flavorId;

  @Size(min = Constants.DEFAULT_NAME_MIN_LENGTH, max = Constants.DEFAULT_NAME_MAX_LENGTH, message = "{size.not.valid}")
  @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "{not.valid}")
  private String flavorName;

  @Range(min = Constants.DEFAULT_CPU_NUM_MIN_LIMIT, max = Constants.DEFAULT_CPU_NUM_MAX_LIMIT, message = "{size.not.valid}")
  private int vcpus;

  @Range(min = Constants.DEFAULT_RAM_SIZE_MIN_LIMIT, max = Constants.DEFAULT_RAM_SIZE_MAX_LIMIT, message = "{size.not.valid}")
  private int ram;

  @Range(min = Constants.DEFAULT_DISK_SIZE_MIN_LIMIT, max = Constants.DEFAULT_DISK_SIZE_MAX_LIMIT, message = "{size.not.valid}")
  private int disk;

  public FlavorModel() {
    super();
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
