package com.inforstack.openstack.payment;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.tenant.Tenant;

public interface PaymentDao extends BasicDao<Payment> {

	List<Payment> findAvailablePayments(Tenant tenant, String paymentUuid);

}
