package com.inforstack.openstack.controller.model;

import java.util.Date;
import java.util.List;

/**
 * vm instance model
 * 
 * @author shaw
 * 
 */
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
  // public up bound
  private List<String> publcips;
  private String privateip;
  private Integer disksize;
  private Integer maxmemory;
  private Integer maxcpu;
}
