package com.inforstack.openstack.billing.invoice;

import java.util.Date;
import java.util.List;

public interface InvoiceDao {

	public void persist(Invoice invoice);

	public Invoice findById(Integer invoiceId);

	public List<Invoice> findByTime(Date from, Date to);

}
