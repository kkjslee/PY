package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.order.OrderServiceImpl;
import com.inforstack.openstack.payment.account.Account;
import com.inforstack.openstack.payment.account.AccountService;
import com.inforstack.openstack.payment.method.PaymentMethod;
import com.inforstack.openstack.payment.method.PaymentMethodService;
import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.DateUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

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
	@Autowired
	private OrderService orderService;
	
	private static String cachedDate = null;
	private static int sequence = 0;
	
	@PostConstruct
	public void postInit(){
		synchronized (PaymentServiceImpl.class){
			if(cachedDate == null){
				Date date = new Date();
				Payment payment = paymentDao.findLastestBySequenceDate("sequence", date);
				int preLen = Constants.SEQUENCE_PREFIX_PAYMENT.length();
				if(payment != null){
					cachedDate = payment.getSequence().substring(0+preLen, DateUtil.SEQ_DATE_LEN + 1 + preLen);
					sequence = new Integer(payment.getSequence().substring(DateUtil.SEQ_DATE_LEN + 1 + preLen));
				}else{
					cachedDate = DateUtil.getSequenceDate(date);
					sequence = 0;
				}
			}
		}
	}
	
	private String genenratePaymentSequence(){
		synchronized (OrderServiceImpl.class) {
			int max = new Integer(StringUtil.leftPadding("", '9', DateUtil.SEQ_DATE_LEN));
			if(sequence == max){
				throw new ApplicationRuntimeException("Max payment limit reached today");
			}
			
			String date = DateUtil.getSequenceDate(new Date());
			if(!date.equals(cachedDate)){
				cachedDate = date;
				sequence = 0;
			}
			sequence++;
			return Constants.SEQUENCE_PREFIX_PAYMENT + date + NumberUtil.leftPaddingZero(sequence, 8);
		}
	}
	
	@Override
	public Payment createPayment(String subject, BigDecimal amount, int type, int tenantId, Integer instaceId) {
		Account account = null;
		if(instaceId != null){
			account = accountService.findOrCreateActiveAccount(
					tenantService.findTenantById(tenantId), 
					instanceService.findInstanceById(instaceId));
		}else{
			account = accountService.findOrCreateActiveAccount(
					tenantService.findTenantById(tenantId), null);
		}
		
		return this.createPayment(subject, amount, type, account);
	}
	
	@Override
	public Payment createPayment(String subject, BigDecimal amount, int type, Account account) {
		log.debug("Create payment for type : " + type + ", amount : " + amount + " for account : " + account.getId());
		Payment payment = new Payment();
		payment.setAmount(amount);
		payment.setCreateTime(new Date());
		payment.setType(type);
		payment.setStatus(Constants.PAYMENT_STATUS_NEW);
		payment.setAccount(account);
		payment.setSequence(genenratePaymentSequence());
		payment.setSubject(subject);
		
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}
	
	@Override
	public Payment topup(String subject, BigDecimal amount){
		Payment payment = this.createPayment(subject, amount, Constants.PAYMENT_TYPE_TOPUP, accountService.findOrCreateActiveAccount(SecurityUtils.getTenant(), null));
		this.paidSuccessfully(payment);
		return payment;
	}
	
	@Override
	public Payment topup(String subject, BigDecimal amount, Account account){
		Payment payment = this.createPayment(subject, amount, Constants.PAYMENT_TYPE_TOPUP, account);
		this.paidSuccessfully(payment);
		return payment;
	}
	
	@Override
	public Payment topup(Integer paymentId) {
		Payment payment = paymentDao.findById(paymentId);
		return this.topup(payment.getMethod().getText().getI18nContent(), 
				payment.getAmount().negate(), payment.getAccount());
	}
	
	@Override
	public void paidSuccessfully(Payment payment){
		paymentDao.lock(payment.getAccount(), LockModeType.PESSIMISTIC_WRITE);
		accountService.changeAccount(payment.getAccount(), payment.getAmount(), 
				payment.getType() != Constants.PAYMENT_TYPE_TOPUP);
		payment.setStatus(Constants.PAYMENT_STATUS_OK);
	}

	@Override
	public Payment paymentPocessing(int paymentId) {
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
	public BigDecimal applyPayment(int invoiceId) {
		log.debug("Apply payment to invoice : " + invoiceId);
		Invoice invoice = invoiceService.findInvoice(invoiceId);
		if(invoice == null){
			log.error("No invoice found by id : " + invoiceId);
			throw new ApplicationRuntimeException("No invoice found");
		}
		
		BigDecimal balance = this.applyPayment(invoice);
		log.debug("apply payment successfully");
		return balance;
	}

	@Override
	public BigDecimal applyPayment(Invoice invoice) {
		return this.applyPayment(invoice, null);
	}
	
	public BigDecimal applyPayment(Invoice invoice, Payment payment){
		log.debug("Apply payment to invoice : " + invoice.getId());
		BigDecimal balance = this.applyPayment(invoice, payment, invoice.getSubOrder().getOrderPeriod().getPayAsYouGo());
		log.debug("apply payment successfully");
		return balance;
	}

	@Override
	public BigDecimal applyPayment(Invoice invoice, Payment payment, boolean payasyougo) {
		log.debug("Apply payment to invoice : " + invoice.getId() + ", payasyougo : " + payasyougo);
		Account account = null;
		if(payment != null){
			account = payment.getAccount();
		}else{
			if(payasyougo){
				account = accountService.findOrCreateActiveAccount(invoice.getTenant(), invoice.getSubOrder().getInstance());
			}else{
				account = accountService.findOrCreateActiveAccount(invoice.getTenant(), null);
			}
		}
		
		paymentDao.lock(account, LockModeType.PESSIMISTIC_WRITE);
		BigDecimal payAmount = null;
		if(account.getBalance().compareTo(invoice.getBalance()) >= 0){
			payAmount = invoice.getBalance();
		}else{
			payAmount = account.getBalance();
		}
		
		if(payment == null){
			payment = this.createPayment(invoice.getSequence(), payAmount.negate(), Constants.PAYMENT_TYPE_PAYOUT, account);
		}else{
			if(payAmount.compareTo(payment.getAmount()) < 0){
				throw new ApplicationRuntimeException(OpenstackUtil.getMessage("acount.money.not.enough"));
			}
		}
		BigDecimal balance = invoiceService.payAmount(invoice, payAmount);
		this.paidSuccessfully(payment);
		
		if(payAmount.compareTo(BigDecimal.ZERO) > 0){
			if(invoice.getSubOrder() != null){
				invoice.getSubOrder().setLastPayTime(new Date());
				invoice.getSubOrder().getOrder().setLastPayTime(new Date());
			}
		}
		
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
	public BigDecimal authorisation(Payment payment){
		if(payment.getType() != Constants.PAYMENT_TYPE_AUTHORISATION){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
		}
		if(payment.getAmount().compareTo(BigDecimal.ZERO) > 0){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("acount.authorise.illegal"));
		}
		if(payment.getAccount().getBalance().add(payment.getAmount()).compareTo(BigDecimal.ZERO) < 0){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("acount.money.not.enough"));
		}
		this.paidSuccessfully(payment);
		
		return payment.getAccount().getBalance();
	}
	
	@Override
	public Payment payAuthorisation(Payment payment){
		if(payment.getType() != Constants.PAYMENT_TYPE_AUTHORISATION){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
		}
		if(payment.getAmount().compareTo(BigDecimal.ZERO) > 0){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("acount.authorise.illegal"));
		}
		Payment newPayment = this.createPayment(payment.getSubject(), payment.getAmount(), Constants.PAYMENT_TYPE_PAYOUT, payment.getAccount());
		newPayment.setStatus(Constants.PAYMENT_STATUS_OK);
		payment.setStatus(Constants.PAYMENT_STATUS_DELETED);
		
		return newPayment;
	}

	@Override
	public String generateEndpoint(int paymentMethodId, BigDecimal balance, Map<String, Object> property) {
		PaymentMethod pm = paymentMethodService.findPaymentMethodById(paymentMethodId);
		if(pm == null){
			log.error("No payment method found by id : " +paymentMethodId);
			throw new ApplicationRuntimeException("payment method not found");
		}
		
		List<PaymentMethodProperty> pmps = paymentMethodService.findParams(pm.getId(), balance.doubleValue(), property);
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
		
		StringBuilder endPoint = new StringBuilder(OpenstackUtil.setProperty(pm.getEndpoint(), property));
		if(param.length()==0){
			return endPoint.toString();
		}else{
			return endPoint.append("?").append(param).toString();
		}
	}

	@Override
	public Payment findPaymentById(int paymentId) {
		return paymentDao.findById(paymentId);
	}
	
	@Override
	public Payment processPayment(int paymentId){
		Payment payment = this.findPaymentById(paymentId);
		if(payment == null){
			throw new ApplicationRuntimeException("Payment not found");
		}
		
		paymentDao.lock(payment.getAccount(), LockModeType.PESSIMISTIC_WRITE);
		if(Constants.PAYMENT_TYPE_AUTHORISATION.equals(payment.getType())){
			this.authorisation(payment);
		}else if(Constants.PAYMENT_TYPE_PAYOUT.equals(payment.getType())){
			String subject = payment.getSubject();
			if(subject.startsWith(Constants.SEQUENCE_PREFIX_ORDER)){
				Order order = orderService.findOrderBySequence(subject);
				if(order == null){
					throw new ApplicationRuntimeException(
							OpenstackUtil.getMessage("order.not.found") + " : " + subject);
				}
				orderService.fullPayment(order.getId(), payment.getId());
			}else if(subject.startsWith(Constants.SEQUENCE_PREFIX_INVOICE)){
				Invoice invoice = invoiceService.findInvoiceBySequence(subject);
				if(invoice == null){
					throw new ApplicationRuntimeException(
							OpenstackUtil.getMessage("invoice.not.found") + " : " + subject);
				}
				invoiceService.fullPayment(invoice.getId(), payment.getId());
			}else{
				throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
			}
		}else{
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
		}
		
		return payment;
	}

}
