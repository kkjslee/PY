package com.inforstack.openstack.order.period;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface OrderPeriodDao extends BasicDao<OrderPeriod> {

	public List<OrderPeriod> findAll(boolean includeDeleted);
}
