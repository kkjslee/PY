package com.inforstack.openstack.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

  public static final String SESSION_ATTRIBUTE_NAME_AGENT = Constants.class.getName() + ".Agent";

  public static final int USER_STATUS_INVALID = 0;
  public static final int USER_STATUS_VALID = 1;

  public static final int USER_AGEING_ACTIVE = 1;
  public static final int USER_AGEING_DELETED = 2;

  public static final int TENANT_AGEING_ACTIVE = 1;
  public static final int TENANT_AGEING_DELETED = 2;

  public static final int IMG_PROGRESS_ACTIVE = 100;
  public static final String IMG_STATUS_ACTIVE = "ACTIVE";

  public static final String IMG_METADATA_KEY_KERNEL = "kernel_id";
  public static final String IMG_METADATA_KEY_RAMDISK = "ramdisk_id";

  public static final String JSON_STATUS_EXCEPTION = "{\"status\":\"exception\"}";
  public static final String JSON_STATUS_DONE = "{\"status\":\"done\"}";
  public static final String JSON_STATUS_FAILED = "{\"status\":\"failed\"}";

  // compatible with font side
  public static final Map<String, String> vmStatusMap = new HashMap<String, String>();
  // key is the same as openstack status, and value is the same as front
  static {
    vmStatusMap.put("active", "RUNNING");
    vmStatusMap.put("pause", "PAUSED");
    vmStatusMap.put("rebooting", "RESUMING");
    vmStatusMap.put("building", "CREATING");
    vmStatusMap.put("stopped", "SHUTOFF");
    vmStatusMap.put("resized", "RESIZED");
    vmStatusMap.put("suspended", "SUSPENDED");
    vmStatusMap.put("resuming", "RESUMING");
  }

}
