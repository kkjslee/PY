package com.inforstack.openstack.billing.invoice;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface InvoiceDao extends BasicDao<Invoice>  {

	public List<Invoice> findByTime(Date from, Date to);

}
