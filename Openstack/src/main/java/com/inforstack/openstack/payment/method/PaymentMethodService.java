package com.inforstack.openstack.payment.method;

public interface PaymentMethodService {
	
	/**
	 * find payment method by id
	 * @param paymentMethodId
	 * @return
	 */
	public PaymentMethod findPaymentMethodById(int paymentMethodId);

}
