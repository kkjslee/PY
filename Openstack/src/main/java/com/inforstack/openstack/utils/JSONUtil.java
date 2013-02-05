package com.inforstack.openstack.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * {status:success,data:obj,msg:""} or {status:error, data:obj, msg:"msg"}
 * 
 * @author rqshao
 * 
 */
public class JSONUtil {

	public static Map<String, Object> jsonSuccess(Object data, String msg) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Constants.JSON_STATUS, Constants.JSON_SUCCESS_STATUS);
		if (!StringUtil.isNullOrEmpty(msg, true)) {
			returnValue.put(Constants.JSON_MSG, msg);
		}
		if (data != null) {
			returnValue.put(Constants.JSON_DATA, data);
		} else {
			returnValue.put(Constants.JSON_DATA,
					Constants.JSON_OBJECT_DATA_NULL);
		}
		return returnValue;
	}

	public static Map<String, Object> jsonSuccess(Object data) {
		return jsonSuccess(data, null);
	}

	public static Map<String, Object> jsonError(Object data, String msg) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Constants.JSON_STATUS, Constants.JSON_ERROR_STATUS);
		if (!StringUtil.isNullOrEmpty(msg, true)) {
			returnValue.put(Constants.JSON_MSG, msg);
		}
		if (data != null) {
			returnValue.put(Constants.JSON_DATA, data);
		} else {
			returnValue.put(Constants.JSON_DATA,
					Constants.JSON_OBJECT_DATA_NULL);
		}
		return returnValue;
	}

	public static Map<String, Object> jsonError(String msg) {
		return jsonError(null, msg);
	}

}
