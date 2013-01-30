package com.inforstack.openstack.order.period;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface OrderPeriodDao extends IDao<OrderPeriod> {

	public List<OrderPeriod> findAll(boolean includeDeleted);
}
