package com.inforstack.openstack.billing.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

	private static final Log log = LogFactory.getLog(InvoiceServiceImpl.class);
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Override
	public Invoice createInvoice(Date startTime, Date endTime, BigDecimal amount, Tenant tenant, 
			SubOrder subOrder, Order order, BillingProcess billingProcess) {
		log.debug("Create invoice with startTime : " + startTime + ", endTime : " +  endTime + ", amount : " +
				amount + ", tenant : " + tenant.getId() + ", subOrder : " + subOrder.getId()+ "order : " +
				order.getId() + ", billingProcess : " + billingProcess.getId());
		Invoice invoice = new Invoice();
		invoice.setAmount(amount);
		invoice.setBanlance(amount);
		invoice.setBillingProcess(billingProcess);
		invoice.setCreateTime(new Date());
		invoice.setStartTime(startTime);
		invoice.setEndTime(endTime);
		invoice.setSubOrder(subOrder);
		invoice.setOrder(order);
		invoice.setTenant(tenant);
		invoice.setStatus(Constants.INVOICE_STATUS_NEW);
		
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
	public List<Invoice> findInvoice(Date from, Date to) {
		log.debug("Find invoice created from : " + from + ", to : " + to);
		
		List<Invoice> invoices = invoiceDao.findByTime(from, to);
		if(CollectionUtil.isNullOrEmpty(invoices)){
			log.debug("No invoices found");
		}else{
			log.debug(invoices.size() + " invoices found");
		}
		
		return invoices;
	}
	
	
	
}
