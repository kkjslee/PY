package com.inforstack.openstack.payment.method.prop.builder;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;
import com.inforstack.openstack.utils.CryptoUtil;
import com.inforstack.openstack.utils.StringUtil;

public class AlipaySignParamBuilder implements ParamBuilder {

	@Override
	public String build(List<PaymentMethodProperty> params,
			Map<String, String> propMap) {
		Set<String> exclude = new HashSet<String>();
		exclude.add("sign_type");
		
		String sign_type = null;
		List<PaymentMethodProperty> sorted = sort(params);
		StringBuilder builder = null;
		for(PaymentMethodProperty pmp : sorted){
			String name = pmp.getName();
			if(StringUtil.isNullOrEmpty(name) || exclude.contains(name)){
				if("sign_type".equals(name)){
					sign_type = pmp.getValue();
				}
				continue;
			}
			
			if(builder == null){
				builder = new StringBuilder();
			}else{
				builder.append("&");
			}
			builder.append(name).append("=").append(StringUtil.getStringFromEmpty(pmp.getValue()));
		}
		if(builder == null){
			builder = new StringBuilder();
		}
		
		String key = propMap.get("privatekey");
		if("MD5".equals(sign_type)){
			return CryptoUtil.md5(builder.append(key).toString());
		}else if("RSA".equals(sign_type)){
			throw new ApplicationRuntimeException("Unsupported sign type : " + sign_type);
		}else if("DSA".equals(sign_type)){
			throw new ApplicationRuntimeException("Unsupported sign type : " + sign_type);
		}else{
			throw new ApplicationRuntimeException("Unsupported sign type : " + sign_type);
		}
	}

	private List<PaymentMethodProperty> sort(List<PaymentMethodProperty> params) {
		Collections.sort(params, new Comparator<PaymentMethodProperty>() {

			@Override
			public int compare(PaymentMethodProperty o1,
					PaymentMethodProperty o2) {
				return ("" + o1.getName() + o1.getValue()).compareTo((""
						+ o2.getName() + o2.getValue()));
			}
		});
		
		return params;
	}

}
