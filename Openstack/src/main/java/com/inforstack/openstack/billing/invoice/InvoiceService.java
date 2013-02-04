package com.inforstack.openstack.billing.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.tenant.Tenant;

public interface InvoiceService {
	
	/**
	 * Create invoice with necessary fields
	 * @param startTime
	 * @param endTime
	 * @param amount
	 * @param tenant
	 * @param subOrder
	 * @param order
	 * @param billingProcess
	 * @return
	 */
	public Invoice createInvoice(Date startTime, Date endTime, BigDecimal amount, Tenant tenant, 
			SubOrder subOrder, Order order, BillingProcess billingProcess);
	
	/**
	 * delete invoices
	 * @param invoice managed
	 * @return
	 */
	public Invoice deleteInvoice(Invoice invoice);
	
	/**
	 * delete invoice
	 * @param invoiceId
	 * @return
	 */
	public Invoice deleteInvoice(int invoiceId);
	
	/**
	 * find invoice
	 * @param invoiceId
	 * @return
	 */
	public Invoice findInvoice(int invoiceId);
	
	/**
	 * Find invoices by createTime
	 * @param from create time >= form time, or null for no condition
	 * @param to create time <= to time, or null for no condition
	 * @return
	 */
	public List<Invoice> findInvoice(Date from, Date to);
	
}
