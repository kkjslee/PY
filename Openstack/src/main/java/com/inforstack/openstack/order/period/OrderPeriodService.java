package com.inforstack.openstack.order.period;

import java.util.List;
import java.util.Map;

public interface OrderPeriodService {
	
	/**
	 * create order period
	 * @param period managed period instance
	 * @return
	 */
	public OrderPeriod createPeriod(OrderPeriod period); 
	
	/**
	 * create order period with necessary fileds
	 * @param nameMap
	 * @param type
	 * @param quantity
	 * @return
	 */
	public OrderPeriod createPeriod(Map<Integer, String> nameMap, int type, int quantity);
	
	/**
	 * delete order period
	 * @param period
	 * @return
	 */
	public OrderPeriod deletePeriod(OrderPeriod period);
	
	/**
	 * delete order period
	 * @param periodId
	 * @return
	 */
	public OrderPeriod deletePeriod(int periodId);
	
	/**
	 * list all order periods
	 * @param includeDeleted
	 * @return
	 */
	public List<OrderPeriod> listAll(boolean includeDeleted);
	
	/**
	 * find order period by id
	 * @param periodId
	 * @return
	 */
	public OrderPeriod findPeriodById(int periodId);

	List<OrderPeriod> findPeriodsByProcessId(int billingProcessConfigId);
}
