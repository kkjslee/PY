package com.inforstack.openstack.payment;

import com.inforstack.openstack.tenant.Tenant;

public interface PaymentService {

	/**
	 * Create payment
	 * @param payment
	 * @return
	 */
	public Payment createPayment(Payment payment);
	
	/**
	 * Create payment with necessary fileds
	 * @param tenant
	 * @param amount
	 * @param isRefund
	 * @param type
	 * @return
	 */
	public Payment createPayment(Tenant tenant, double amount, boolean isRefund, int type);
	
	/**
	 *  Create payment with necessary fileds
	 * @param tenantId
	 * @param amount
	 * @param isRefund
	 * @param type
	 * @return
	 */
	public Payment createPayment(int tenantId, double amount, boolean isRefund, int type);
	
	/**
	 * process payment
	 * @param paymentId
	 * @return
	 */
	public Payment processPayment(int paymentId);
}
