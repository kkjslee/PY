package com.inforstack.openstack.payment.method.prop;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface PaymentMethodPropertyDao extends BasicDao<PaymentMethodProperty>{

	List<PaymentMethodProperty> findbyMethodAndType(Integer paymentMethodId, Integer type);

}
