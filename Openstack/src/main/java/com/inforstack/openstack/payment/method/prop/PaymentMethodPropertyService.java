package com.inforstack.openstack.payment.method.prop;

import java.util.List;

public interface PaymentMethodPropertyService {
	
	PaymentMethodProperty findById(int paymentMethodPropertyId);

	List<PaymentMethodProperty> findParams(int paymentMethodId);

	List<PaymentMethodProperty> findProps(int paymentMethodId);

	List<PaymentMethodProperty> findMethodParams(int paymentMethodId);

}
