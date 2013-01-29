package com.inforstack.openstack.billing.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.inforstack.openstack.billing.process.BillingProcess;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.sub.SubOrder;
import com.inforstack.openstack.tenant.Tenant;

public interface InvoiceService {
	
	public Invoice createInvoice(Date startTime, Date endTime, BigDecimal amount, Tenant tenant, 
			SubOrder subOrder, Order order, BillingProcess billingProcess);
	
	public Invoice deleteInvoice(Invoice invoice);

	public Invoice deleteInvoice(Integer invoiceId);
	
	public Invoice findInvoice(Integer invoiceId);
	
	public List<Invoice> findInvoice(Date from, Date to);
}
