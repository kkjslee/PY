package com.inforstack.openstack.billing.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.payment.Payment;
import com.inforstack.openstack.payment.PaymentService;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.DateUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	private static final Logger log = new Logger(InvoiceServiceImpl.class);
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private PaymentService paymentService;
	
	private static String cachedDate = null;
	private static int sequence = 0;
	
	@PostConstruct
	public void postInit(){
		synchronized (InvoiceServiceImpl.class){
			if(cachedDate == null){
				Date date = new Date();
				Invoice invoice = invoiceDao.findLastestBySequenceDate("sequence", date);
				int preLen = Constants.SEQUENCE_PREFIX_INVOICE.length();
				if(invoice != null){
					cachedDate = invoice.getSequence().substring(0+preLen, DateUtil.SEQ_DATE_LEN + 1 + preLen);
					sequence = new Integer(invoice.getSequence().substring(DateUtil.SEQ_DATE_LEN + 1 + preLen));
				}else{
					cachedDate = DateUtil.getSequenceDate(date);
					sequence = 0;
				}
			}
		}
	}
	
	private String genenrateInvoiceSequence(){
		synchronized (InvoiceServiceImpl.class) {
			int max = new Integer(StringUtil.leftPadding("", '9', DateUtil.SEQ_DATE_LEN));
			if(sequence == max){
				throw new ApplicationRuntimeException("Max account limited today");
			}
			
			String date = DateUtil.getSequenceDate(new Date());
			if(!date.equals(cachedDate)){
				cachedDate = date;
				sequence = 0;
			}
			sequence++;
			return Constants.SEQUENCE_PREFIX_INVOICE + date + NumberUtil.leftPaddingZero(sequence, 8);
		}
	}
	
	@Override
	public Invoice createInvoice(Date startTime, Date endTime, BigDecimal amount, Tenant tenant, 
			SubOrder subOrder, Order order, BillingProcess billingProcess) {
		log.debug("Create invoice with startTime : " + startTime + ", endTime : " +  endTime + ", amount : " +
				amount + ", tenant : " +  tenant.getId() + ", subOrder : " + (subOrder==null?"":subOrder.getId())+ "order : " +
				order.getId() + ", billingProcess : " + billingProcess.getId());
		Invoice invoice = new Invoice();
		invoice.setAmount(amount);
		invoice.setBalance(amount);
		invoice.setBillingProcess(billingProcess);
		invoice.setCreateTime(new Date());
		invoice.setStartTime(startTime);
		invoice.setEndTime(endTime);
		invoice.setSubOrder(subOrder);
		invoice.setOrder(order);
		invoice.setTenant(tenant);
		invoice.setStatus(Constants.INVOICE_STATUS_UNPAID);
		invoice.setSequence(genenrateInvoiceSequence());
		
		invoiceDao.persist(invoice);
		log.debug("Create invoice successfully");
		
		return invoice;
	}

	@Override
	public Invoice deleteInvoice(Invoice invoice) {
		log.debug("Delete invoice : " + invoice.getId());
		invoice.setStatus(Constants.INVOICE_STATUS_DELETED);
		log.debug("Delete invoice successfully");
		
		return invoice;
	}

	@Override
	public Invoice deleteInvoice(int invoiceId) {
		log.debug("Delete invoice by id : " + invoiceId);
		Invoice invoice = invoiceDao.findById(invoiceId);
		if(invoice == null){
			log.info("Delete invoice failed for no invoice found by id : " + invoiceId);
			return null;
		}
		invoice.setStatus(Constants.INVOICE_STATUS_DELETED);
		log.debug("Delete invoice successfully");
		return invoice;
	}

	@Override
	public Invoice findInvoice(int invoiceId) {
		log.debug("Find invoice by id : " + invoiceId);
		Invoice invoice = invoiceDao.findById(invoiceId);
		if(invoice == null){
			log.debug("Find invoice failed");
		}else{
			log.debug("Find invoice successfully");
		}
		
		return invoice;
	}

	@Override
	public PaginationModel<Invoice> findInvoice(int pageIndex, int pageSize, Integer tenantId, Date from, Date to) {
		log.debug("Find invoice created from : " + from + ", to : " + to);
		
		PaginationModel<Invoice> model = invoiceDao.findByTime(pageIndex, pageSize, tenantId, from, to);
		if(CollectionUtil.isNullOrEmpty(model.getData())){
			log.debug("No invoices found");
		}else{
			for(Invoice iv: model.getData()){
				if(iv.getSubOrder() != null){
					iv.getSubOrder().getOrderPeriod().getName().getId();
				}
				iv.getOrder().getId();
				
			}
			log.debug(model.getData().size() + " invoices found");
		}
		
		return model;
	}

	@Override
	public BigDecimal payAmount(Invoice invoice, BigDecimal payAmount) {
		if(invoice.getBalance().compareTo(payAmount) < 0){
			log.warn("Payment is bigger than invoie amount");
		}
		invoice.setBalance(invoice.getBalance().subtract(payAmount));
		return invoice.getBalance();
	}

	@Override
	public void paid(Invoice invoice) {
		invoice.setStatus(Constants.INVOICE_STATUS_PAID);
	}

	@Override
	public void unpaid(Invoice invoice) {
		invoice.setStatus(Constants.INVOICE_STATUS_UNPAID);
	}

	@Override
	public List<Invoice> findInvoicesByBillingProcess(
			int billingProcessId) {
		List<Invoice> invoices =  invoiceDao.listByObject("billingProcess.id", billingProcessId);
		if(invoices == null){
			invoices = new ArrayList<Invoice>();
		}
		
		return invoices;
	}

	@Override
	public Invoice findInvoiceBySequence(String subject) {
		return invoiceDao.findByObject("sequence", subject);
	}

	@Override
	public List<Invoice> findUnPaidInvoicesByOrder(int orderId) {
		return invoiceDao.findInvoices(Constants.INVOICE_STATUS_UNPAID, orderId);
	}

	@Override
	public boolean fullPayment(int invoiceId, int paymentId) {
		Invoice invoice = this.findInvoice(invoiceId);
		Payment payment = paymentService.findPaymentById(paymentId);
		if(invoice == null || payment == null){
			return false;
		}
		
		if(payment.getAccount().getTenant().getId().equals(invoice.getTenant().getId())){
			return false;
		}
		
		if(payment.getAmount().compareTo(payment.getAccount().getAmount()) > 0 ){
			return false;
		}
		if(!Constants.PAYMENT_CATALOG_PAYOUT.equals(payment.getType())){
			throw new ApplicationRuntimeException(OpenstackUtil.getMessage("payment.type.not.support"));
		}
		BigDecimal balance = paymentService.applyPayment(invoice, payment);
		if(balance.compareTo(BigDecimal.ZERO) != 0){
			throw new ApplicationRuntimeException("Full payment failed");
		}
		paymentService.paidSuccessfully(payment);
		
		return true;
	}
}
