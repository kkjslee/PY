package com.inforstack.openstack.payment.method;

import java.util.List;

import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;

public interface PaymentMethodService {
	
	/**
	 * find payment method by id
	 * @param paymentMethodId
	 * @return
	 */
	public PaymentMethod findPaymentMethodByType(int type);

	public List<PaymentMethod> listAll();

	List<PaymentMethodProperty> findParams(int paymentMethodId, double price);

}
