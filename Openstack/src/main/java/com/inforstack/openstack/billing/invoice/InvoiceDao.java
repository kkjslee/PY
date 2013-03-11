package com.inforstack.openstack.billing.invoice;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.controller.model.PaginationModel;

public interface InvoiceDao extends BasicDao<Invoice>  {

	public PaginationModel<Invoice> findByTime(int pageIndex, int pageSize, Integer tenantId, Date from, Date to);

	public List<Invoice> findInvoices(Integer status, Integer orderId);

}
