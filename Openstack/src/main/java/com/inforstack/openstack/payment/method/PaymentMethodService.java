package com.inforstack.openstack.payment.method;

import java.util.List;

public interface PaymentMethodService {
	
	/**
	 * find payment method by id
	 * @param paymentMethodId
	 * @return
	 */
	public PaymentMethod findPaymentMethodById(int paymentMethodId);

	public List<PaymentMethod> listAll();

}
