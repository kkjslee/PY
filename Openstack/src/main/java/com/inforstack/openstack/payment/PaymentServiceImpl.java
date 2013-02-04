package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service(value="paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	private static final Logger log = new Logger(PaymentServiceImpl.class);
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private TenantService tenantService;
	
	@Override
	public Payment createPayment(Payment payment) {
		if(payment == null){
			log.info("Create payment failed for passed payment is null");
			return null;
		}
		
		log.debug("Create paymant");
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}
	
	@Override
	public Payment createPayment(Tenant tenant, double amount, boolean isRefund, int type) {
		if(tenant==null){
			log.info("Create payment failed for passed tenant is null");
			return null;
		}
		
		log.debug("Create payment for tenant : " + tenant.getName());
		Payment payment = new Payment();
		BigDecimal amoutBD = new BigDecimal(amount);
		payment.setAmount(amoutBD);
		payment.setBalance(amoutBD);
		payment.setCreateTime(new Date());
		payment.setType(type);
		payment.setStatus(Constants.PAYMENT_STATUS_NEW);
		payment.setTenant(tenant);
		
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}

	@Override
	public Payment createPayment(int tenantId, double amount,
			boolean isRefund, int type) {
		log.debug("Create payment for tenant : " + tenantId);
		Tenant tenant = tenantService.findTenantById(tenantId);
		if(tenant == null){
			log.info("Create payment failed for no tenant found by id : " + tenantId);
			return null;
		}
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		Payment payment = self.createPayment(tenant, amount, isRefund, type);
		
		if(payment == null){
			log.debug("Create payment failed");
		}else{
			log.debug("Create payment successfully");
		}
		return payment;
	}

	@Override
	public Payment processPayment(int paymentId) {
		log.debug("Process payment : " + paymentId);
		Payment payment = paymentDao.findById(paymentId);
		if(payment==null){
			log.info("Process payment failed for no payment found by id : " + paymentId);
			return null;
		}
		
		payment.setStatus(Constants.PAYMENT_STATUS_PROCESSING);
		log.debug("Process payment status successfully");
		return payment;
	}

	@Override
	public BigDecimal applyPayment(Invoice invoice) {
		log.debug("Apply payment to invoice : " + invoice.getId());
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		BigDecimal balance = self.applyPayment(invoice, invoice.getSubOrder().getType());
		log.debug("apply payment successfully");
		return balance;
	}

	@Override
	public BigDecimal applyPayment(Invoice invoice, int type) {
		log.debug("Apply payment to invoice : " + invoice.getId() + ", sub order type : " + type);
		String paymentUuid = null;
		if(Constants.SUBORDER_TYPE_POSTPAID == type){
			paymentUuid = invoice.getUuid();
		}
		List<Payment> payments = paymentDao.findAvailablePayments(invoice.getTenant(), paymentUuid);
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		for(Payment payment : payments){
			BigDecimal balance = self.applyPayment(invoice, payment);
			if(balance.equals(BigDecimal.ZERO)){
				break;
			}
		}
		
		int val = invoice.getBalance().compareTo(BigDecimal.ZERO);
		if(val == 0){
			log.debug("Payment successfully applied to invoice : " + invoice);
		}else if(val > 0){
			log.debug("Invoice payment not finished");
		}else{
			log.error("Invoice banlance is less than zero");
		}
		
		return invoice.getBalance();
	}
	
	@Override
	public BigDecimal applyPayment(Invoice invoice, Payment payment){
		log.debug("Apply payment : " + payment.getId() + " to invoice : " + invoice.getId());
		BigDecimal pb = payment.getBalance();
		BigDecimal iv = invoice.getBalance();
		BigDecimal balance = iv.subtract(pb);
		if(balance.compareTo(BigDecimal.ZERO) > 0){
			payment.setStatus(Constants.PAYMENT_STATUS_USEDUP);
			payment.setBalance(BigDecimal.ZERO);
			invoice.setBalance(balance);
		}else if(balance.compareTo(BigDecimal.ZERO) == 0){
			payment.setStatus(Constants.PAYMENT_STATUS_USEDUP);
			invoice.setStatus(Constants.INVOICE_STATUS_PAID);
			payment.setBalance(BigDecimal.ZERO);
			invoice.setBalance(BigDecimal.ZERO);
		}else{
			invoice.setStatus(Constants.INVOICE_STATUS_PAID);
			payment.setBalance(balance.abs());
			invoice.setBalance(BigDecimal.ZERO);
		}
		log.debug("Apply payment to invoice successfully");
		
		return invoice.getBalance();
	}
}
