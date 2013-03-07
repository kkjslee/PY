package com.inforstack.openstack.payment;

import java.math.BigDecimal;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.payment.account.Account;
import com.inforstack.openstack.payment.method.PaymentMethod;

public interface PaymentService {

	/**
	 * Create payment
	 * @param payment
	 * @return
	 */
	public Payment createPayment(Payment payment);
	
	/**
	 * Create payment with necessary fileds
	 * @param amount
	 * @param isRefund
	 * @param type
	 * @return
	 */
	public Payment createPayment(double amount, int type, int tenantId, Integer instaceId);
	
	Payment createPayment(BigDecimal amount, int type, int tenantId, Integer instaceId);
	
	Payment createPayment(BigDecimal amount, int type, Account account);
	
	/**
	 * process payment
	 * @param paymentId
	 * @return
	 */
	public Payment processPayment(int paymentId);
	
	/**
	 * apply payments to invoice
	 * @param invoice
	 */
	public BigDecimal applyPayment(Invoice invoice);
	
	/**
	 * apply payments to invoice
	 * @param invoice
	 * @param type sub order type : perpaid or postpaid
	 */
	public BigDecimal applyPayment(Invoice invoice, boolean payasyougo);

	void paidSuccessfully(Payment payment);

	public String generateEndpoint(int paymentMethodId, BigDecimal balance,
			Order order);

}
