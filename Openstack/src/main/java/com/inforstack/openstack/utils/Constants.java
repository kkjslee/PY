package com.inforstack.openstack.utils;

public class Constants {

	public static final String SESSION_ATTRIBUTE_NAME_AGENT = Constants.class
			.getName() + ".Agent";

	public static final int USER_STATUS_INVALID = 0;
	public static final int USER_STATUS_VALID = 1;

	public static final int USER_AGEING_ACTIVE = 1;
	public static final int USER_AGEING_DELETED = 2;

	public static final int ORDER_STATUS_NEW = 1;
	public static final int ORDER_STATUS_PROCESSING = 2;
	public static final int ORDER_STATUS_READY = 3;
	public static final int ORDER_STATUS_ACTIVE = 4;
	public static final int ORDER_STATUS_FINISHED = 5;
	public static final int ORDER_STATUS_CALLELED = 6;

	public static final int SUBORDER_STATUS_NEW = 1;
	public static final int SUBORDER_STATUS_AVAILABLE = 2;
	public static final int SUBORDER_STATUS_END = 3;
	public static final int SUBORDER_STATUS_ERROR = 4;
	public static final int SUBORDER_STATUS_DELETED = 5;

	public static final int SUBORDER_TYPE_PREPAID = 1;
	public static final int SUBORDER_TYPE_POSTPAID = 2;

	public static final int PAYMENT_STATUS_NEW = 1;
	public static final int PAYMENT_STATUS_PROCESSING = 2;
	public static final int PAYMENT_STATUS_ERROR = 3;
	public static final int PAYMENT_STATUS_OK = 4;

	public static final int PAYMENT_TYPE_TOPUP = 1;
	public static final int PAYMENT_TYPE_REFUND = 2;
	public static final int PAYMENT_TYPE_POINTS = 3;
	public static final int PAYMENT_TYPE_PAYOUT = 3;
	
	public static final int ACCOUNT_STATUS_ACTIVE = 1;
	public static final int ACCOUNT_STATUS_INACTIVE = 2;
	public static final int ACCOUNT_STATUS_DELETED = 3;

	public static final int INVOICE_STATUS_NEW = 1;
	public static final int INVOICE_STATUS_UPPAID = 2;
	public static final int INVOICE_STATUS_PAID = 3;
	public static final int INVOICE_STATUS_OVERDUE = 4;
	public static final int INVOICE_STATUS_DELETED = 5;

	public static final int BILLINGPROCESS_STATUS_NEW = 1;
	public static final int BILLINGPROCESS_STATUS_WAITING = 2;
	public static final int BILLINGPROCESS_STATUS_PROCESSING = 3;
	public static final int BILLINGPROCESS_STATUS_SUCCESS = 4;
	public static final int BILLINGPROCESS_STATUS_PART_SUCCESS = 5;
	public static final int BILLINGPROCESS_STATUS_FAILED = 6;

	public static final int TENANT_AGEING_ACTIVE = 1;
	public static final int TENANT_AGEING_DELETED = 2;

	public static final int REPORT_TYPE_BIllING_PROCESS = 1;

	public static final int REPORT_STATUS_ERROR = 1;

	public static final int VIRTDOMAIN_STATUS_RUNNING = 1;
	public static final int VIRTDOMAIN_STATUS_INPROCESS = 2;
	public static final int VIRTDOMAIN_STATUS_PAUSED = 3;
	public static final int VIRTDOMAIN_STATUS_STOPPED = 4;
	public static final int VIRTDOMAIN_STATUS_SUSPENDED = 5;
	public static final int VIRTDOMAIN_STATUS_ERROR = 6;
	public static final int VIRTDOMAIN_STATUS_DELETED = 7;
	
	public static final int MAILTEMPALTE_TYPE_TEXT = 1;
	public static final int MAILTEMPALTE_TYPE_HTML = 2;

	public static final int IMG_PROGRESS_ACTIVE = 100;
	public static final String IMG_STATUS_ACTIVE = "ACTIVE";

	public static final String IMG_METADATA_KEY_KERNEL = "kernel_id";
	public static final String IMG_METADATA_KEY_RAMDISK = "ramdisk_id";

	public static final String JSON_STATUS_EXCEPTION = "{\"status\":\"exception\"}";
	public static final String JSON_STATUS_DONE = "{\"status\":\"done\"}";
	public static final String JSON_STATUS_FAILED = "{\"status\":\"failed\"}";

	public static final String JSON_STATUS_TRUE = "{\"status\":\"true\"}";
	public static final String JSON_STATUS_FALSE = "{\"status\":\"false\"}";

	public static final String JSON_ERROR_STATUS = "error";
	public static final String JSON_SUCCESS_STATUS = "success";
	public static final String JSON_STATUS = "status";
	public static final String JSON_DATA = "data";
	public static final String JSON_MSG = "msg";
	public static final String JSON_OBJECT_DATA_NULL = "{}";

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
	public static final int DEFAULT_PAGE_SIZE = 15;

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
	public static final String TABLE_PERIOD = "Period";

	public static final String COLUMN_CATEGORY_NAME = "name_id";
	public static final String COLUMN_ITEMMETADATA_NAME = "name_id";
	public static final String COLUMN_ITEMMETADATA_VALUE = "value_id";
	public static final String COLUMN_ITEMSPECIFICATION_NAME = "name_id";
	public static final String COLUMN_SECURITY_GROUP_DESCRIPTION = "SecurityGroup";
	public static final String COLUMN_PROMOTION_DISPLAYNAME = "DisplayName";
	public static final String COLUMN_PERIOD_NAME = "Name";

	public static final String VM_STATUS_DONE_STRING = "|erroroff|activeoff|pausedoff|stoppedoff|suspendedoff|deletedoff|";

	public static final int PERIOD_TYPE_YEAR = 1;
	public static final int PERIOD_TYPE_MONTH = 2;
	public static final int PERIOD_TYPE_WEEK = 4;
	public static final int PERIOD_TYPE_DAY = 5;
	public static final int PERIOD_TYPE_HOUR = 11;
	public static final int PERIOD_TYPE_MINUTE = 12;

	public static final String DICTIONARY_KEY_PERIOD_TYPE = "period.type";
	public static final String DICTIONARY_KEY_ORDER_STATUS = "order.status";
	public static final String DICTIONARY_KEY_TRUE_FALSE = "true.false";

	public static final int INSTANCE_TYPE_VM = 1;
	public static final int INSTANCE_TYPE_VOLUME = 2;
	public static final int INSTANCE_TYPE_IP = 3;
	public static final int INSTANCE_TYPE_SNAPSHOT_VM = 4;
	public static final int INSTNACE_TYPE_SNAPSHOT_VOL = 5;

	public static final int AJAX_RESPONSE_STATUS_ERROR = 0;
	public static final int AJAX_RESPONSE_STATUS_SUCCESS = 1;

	public static final String AJAX_RESPONSE_KEY_STATUS = "status";
	public static final String AJAX_RESPONSE_KEY_RESULT = "result";

	public static final String CONFIG_SOCKET_IP = "socket.ip";
	public static final String CONFIG_SOCKET_PORT = "socket.port";
	public static final String CONFIG_SOCKET_CEILOMETER_TIMEOUT = "socket.ceilmeter.timeout";
	public static final String CONFIG_SOCKET_TIMESTAMP_TOLERANCE = "socket.timestamp.tolerance";
	public static final String POOLS_SPLITTER = ";";
	public static final String IP_SPLITTER = ",";

	public static final int ATTACH_TASK_TYPE_VOLUME = 1;
	public static final int ATTACH_TASK_TYPE_IP = 2;
	public static final int DETACH_TASK_TYPE_VOLUME = 11;
	public static final int DETACH_TASK_TYPE_IP = 12;

	public static final int ATTACH_TASK_STATUS_NEW = 0;
	public static final int ATTACH_TASK_STATUS_PROCESSING = 1;
	public static final int ATTACH_TASK_STATUS_COMPLETE = 2;
	public static final int ATTACH_TASK_STATUS_ERROR = 3;

	public static final String IMAGE_FAMILY_WINDOWS = "windows";
	public static final String IMAGE_FAMILY_REDHAT = "redhat";
	public static final String IMAGE_FAMILY_CENTOS = "centos";
	public static final String IMAGE_FAMILY_UBUNTU = "ubuntu";
	public static final String IMAGE_FAMILY_MACOS = "macos";
	public static final String IMAGE_FAMILY_OTHER = "other";
	
}
