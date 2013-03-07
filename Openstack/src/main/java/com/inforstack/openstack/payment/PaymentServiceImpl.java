package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.payment.account.Account;
import com.inforstack.openstack.payment.account.AccountService;
import com.inforstack.openstack.payment.method.PaymentMethod;
import com.inforstack.openstack.payment.method.PaymentMethodService;
import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;
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
	private AccountService accountService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private PaymentMethodService paymentMethodService;
	
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
	
	public Payment createPayment(double amount, int type, int tenantId, Integer instaceId) {
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		return self.createPayment(new BigDecimal(amount), type, tenantId, instaceId);
	}
	
	@Override
	public Payment createPayment(BigDecimal amount, int type, int tenantId, Integer instaceId) {
		Account account = null;
		if(instaceId != null){
			account = accountService.findOrCreateActiveAccount(
					tenantService.findTenantById(tenantId), 
					instanceService.findInstanceById(instaceId));
		}else{
			account = accountService.findOrCreateActiveAccount(
					tenantService.findTenantById(tenantId), null);
		}
		
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		return self.createPayment(amount, type, account);
	}
	
	@Override
	public Payment createPayment(BigDecimal amount, int type, Account account) {
		log.debug("Create payment for type : " + type + ", amount : " + amount + " for account : " + account.getId());
		Payment payment = new Payment();
		payment.setAmount(amount);
		payment.setCreateTime(new Date());
		payment.setType(type);
		payment.setStatus(Constants.PAYMENT_STATUS_NEW);
		payment.setAccount(account);
		
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}
	
	@Override
	public void paidSuccessfully(Payment payment){
		payment.setStatus(Constants.PAYMENT_STATUS_OK);
		accountService.changeAccount(payment.getAccount(), payment.getAmount(), 
				payment.getType() != Constants.PAYMENT_TYPE_TOPUP);
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
		BigDecimal balance = self.applyPayment(invoice, invoice.getSubOrder().getOrderPeriod().getPayAsYouGo());
		log.debug("apply payment successfully");
		return balance;
	}

	@Override
	public BigDecimal applyPayment(Invoice invoice, boolean payasyougo) {
		log.debug("Apply payment to invoice : " + invoice.getId() + ", payasyougo : " + payasyougo);
		Account account = null;
		if(payasyougo){
			account = accountService.findOrCreateActiveAccount(invoice.getTenant(), invoice.getSubOrder().getInstance());
		}else{
			account = accountService.findOrCreateActiveAccount(invoice.getTenant(), null);
		}
		
		BigDecimal payAmount = null;
		if(account.getBalance().compareTo(invoice.getBalance()) >= 0){
			payAmount = invoice.getBalance();
		}else{
			payAmount = account.getBalance();
		}
		
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		Payment payment = self.createPayment(payAmount.negate(), Constants.PAYMENT_TYPE_PAYOUT, account);
		BigDecimal balance = invoiceService.payAmount(invoice, payAmount);
		self.paidSuccessfully(payment);
		
		int val = balance.compareTo(BigDecimal.ZERO);
		if(val == 0){
			invoiceService.paid(invoice);
			log.debug("Payment successfully applied to invoice : " + invoice);
		}else if(val > 0){
			invoiceService.unpaid(invoice);
			log.debug("Invoice payment not finished");
		}else{
			log.error("Invoice banlance is less than zero");
		}
		
		return invoice.getBalance();
	}

	@Override
	public String generateEndpoint(int paymentMethodId, BigDecimal balance,
			Order order) {
		PaymentMethod pm = paymentMethodService.findPaymentMethodById(paymentMethodId);
		if(pm == null){
			log.error("No payment method found by id : " +paymentMethodId);
			throw new ApplicationRuntimeException("payment method not found");
		}
		
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(Constants.PAYMENTMETHODPROPERTY_NAME_ORDER, order);
		List<PaymentMethodProperty> pmps = paymentMethodService.findParams(pm.getId(), balance.doubleValue(), props);
		if(pmps == null) {
			pmps = new ArrayList<PaymentMethodProperty>();
		}
		StringBuilder param = new StringBuilder();
		for(PaymentMethodProperty pmp : pmps){
			if(param.length() != 0){
				param.append("&");
			}
			
			param.append(pmp.getName()).append("=").append(pmp.getValue());
		}
		
		if(param.length()==0){
			return pm.getEndpoint();
		}else{
			return pm.getEndpoint() + "?" + param.toString();
		}
	}
	
}
