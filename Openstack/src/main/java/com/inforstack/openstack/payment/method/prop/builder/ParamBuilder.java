package com.inforstack.openstack.payment.method.prop.builder;

import java.util.List;
import java.util.Map;

import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;

public interface ParamBuilder {

	public String build(List<PaymentMethodProperty> params, Map<String, Object> propMap);
	
}
