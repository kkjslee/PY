package com.inforstack.openstack.order.period;

import java.util.List;
import java.util.Map;

public interface OrderPeriodService {
	
	/**
	 * 
	 * @param period managed period instance
	 * @return
	 */
	public OrderPeriod createPeriod(OrderPeriod period); 
	
	public OrderPeriod createPeriod(Map<Integer, String> nameMap, Integer type, Integer quantity);
	
	public OrderPeriod deletePeriod(OrderPeriod period);
	
	public OrderPeriod deletePeriod(Integer periodId);
	
	public List<OrderPeriod> listAll(boolean includeDeleted);
	
	public OrderPeriod findPeriodById(Integer periodId);
}
