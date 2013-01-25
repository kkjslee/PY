package com.inforstack.openstack.order.period;

import java.util.List;

public interface OrderPeriodDao {

	public void persist(OrderPeriod period);
	
	public void merge(OrderPeriod period);
	
	public void remove(OrderPeriod period);

	public OrderPeriod findById(Integer periodId);

	public List<OrderPeriod> findAll(boolean includeDeleted);
}
