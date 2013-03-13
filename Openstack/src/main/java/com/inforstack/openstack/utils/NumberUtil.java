package com.inforstack.openstack.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class NumberUtil {

	/**
	 * 
	 * @param s
	 * @return null if exception occured
	 */
	public static Integer getInteger(String s){
		return getInteger(s, null);
	}
	
	public static Integer getInteger(String s, Integer defaultValue){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static Integer add(Integer i1, Integer i2){
		if(i1 == null){
			i1 = 0;
		}
		if(i2 == null){
			i2 = 0;
		}
		
		return i1+i2;
	}
	
	public static BigDecimal add(BigDecimal bd1, BigDecimal bd2){
		if(bd1 == null){
			bd1 = BigDecimal.ZERO;
		}
		if(bd2 == null){
			bd2 = BigDecimal.ZERO;
		}
		
		return bd1.add(bd2);
	}

	public static BigDecimal minus(BigDecimal bd1, BigDecimal bd2) {
		if(bd1 == null){
			bd1 = BigDecimal.ZERO;
		}
		if(bd2 == null){
			bd2 = BigDecimal.ZERO;
		}
		
		return bd1.subtract(bd2);
	}
	
	public static String leftPaddingZero(int number, int len){
		return StringUtil.leftPadding(number + "", '0', len);
	}
	
	public static String formatMoney(double money){
		return new DecimalFormat("#.00").format(money);
	}
}
