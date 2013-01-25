package com.inforstack.openstack.payment;

import com.inforstack.openstack.tenant.Tenant;

public interface PaymentService {

	public Payment createPayment(Payment payment);
	
	public Payment createPayment(Tenant tenant, double amount, boolean isRefund, int type);
	
	public Payment createPayment(Integer tenantId, double amount, boolean isRefund, int type);
	
	public Payment updatePaymentStatus(Payment payment, int status);
	
	public Payment updatePaymentStatus(Integer paymentId, int status);
}
