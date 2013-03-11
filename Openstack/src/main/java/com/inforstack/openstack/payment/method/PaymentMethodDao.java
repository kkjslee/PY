package com.inforstack.openstack.payment.method;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface PaymentMethodDao extends BasicDao<PaymentMethod> {

	List<PaymentMethod> findMethodsByCatlogs(int[] catlogs);

}
