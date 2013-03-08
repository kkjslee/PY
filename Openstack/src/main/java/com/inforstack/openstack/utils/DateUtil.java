package com.inforstack.openstack.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final int SEQ_DATE_LEN = 8;
	private static final DateFormat seqDateFormat = new SimpleDateFormat("yyyyMMdd");

	public static String getSequenceDate(Date date){
		return seqDateFormat.format(date);
	}
	
}
