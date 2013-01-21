package com.inforstack.openstack.utils;

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

  public static final String JSON_ERROR_STATUS = "error";
  public static final String JSON_SUCCESS_STATUS = "success";

  public static final int DEFAULT_CPU_NUM_MIN_LIMIT = 0;
  public static final int DEFAULT_CPU_NUM_MAX_LIMIT = 65536;
  public static final int DEFAULT_RAM_SIZE_MIN_LIMIT = 0;
  public static final int DEFAULT_RAM_SIZE_MAX_LIMIT = 6553600;
  public static final int DEFAULT_DISK_SIZE_MIN_LIMIT = 0;
  public static final int DEFAULT_DISK_SIZE_MAX_LIMIT = 6553600;

  public static final int DEFAULT_NAME_MIN_LENGTH = 3;
  public static final int DEFAULT_NAME_MAX_LENGTH = 45;

  public static final int DEFAULT_PAGE_INDEX = 0;
  // for test
  public static final int DEFAULT_PAGE_SIZE = 5;

  public static final String PAGER_PAGE_INDEX = "pageIndex";
  public static final String PAGER_PAGE_SIZE = "pageSize";

  public static final int ROLE_ADMIN = 1;
  public static final int ROLE_USER = 2;
  public static final int ROLE_AGENT = 3;

  public static final int LANGUAGE_EN = 1;
  public static final int LANGUAGE_CH = 2;

  public static final String TABLE_CATEGORY = "Category";
  public static final String TABLE_ITEMMETADATA = "ItemMetadata";
  public static final String TABLE_ITEMSPECIFICATION = "ItemSpecification";
  public static final String TABLE_SECURITY_GROUP = "SecurityGroup";
  public static final String TABLE_PROMOTION = "Promotion";
  
  public static final String COLUMN_CATEGORY_NAME = "name_id";
  public static final String COLUMN_ITEMMETADATA_NAME = "name_id";
  public static final String COLUMN_ITEMMETADATA_VALUE = "value_id";
  public static final String COLUMN_ITEMSPECIFICATION_NAME = "name_id";
  public static final String COLUMN_SECURITY_GROUP_DESCRIPTION = "SecurityGroup";
  public static final String COLUMN_PROMOTION_DISPLAYNAME = "DisplayName";

  public static final String VM_STATUS_DONE_STRING = "|activeoff|pausedoff|stoppedoff|suspendedoff|deletedoff|";

}
