package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.Map;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.payment.account.Account;
import com.inforstack.openstack.utils.Constants;

public interface PaymentService {
	
	/**
	 * Create payment with necessary fileds
	 * @param amount
	 * @param isRefund
	 * @param type
	 * @return
	 */
	Payment createPayment(String subject, BigDecimal amount, int type, int tenantId, Integer instaceId);
	
	Payment createPayment(String subject, BigDecimal amount, int type, Account account);
	
	public Payment topup(String subject, BigDecimal amount, int type);
	
	Payment topup(String subject, BigDecimal amount, int type, Account account);
	
	public BigDecimal applyPayment(int invoiceId, int paymentId);
	
	public BigDecimal applyPayment(Invoice invoice, int type, boolean payasyougo);
	
	public BigDecimal applyPayment(Invoice invoice, Payment payment);
	
	Payment payAuthorisation(Payment payment);
	
	public BigDecimal authorisation(Payment payment);
	
	Payment paymentPocessing(int paymentId);

	void paidSuccessfully(Payment payment);

	public String generateEndpoint(int paymentMethodId, BigDecimal balance, Map<String, Object> property);

	Payment findPaymentById(int paymentId);

	/**
	 * process payment
	 * @param paymentId
	 * @return
	 */
	public Payment processPayout(int paymentId);

}
