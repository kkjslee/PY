package com.inforstack.openstack.billing.invoice;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface InvoiceDao extends IDao<Invoice>  {

	public List<Invoice> findByTime(Date from, Date to);

}
