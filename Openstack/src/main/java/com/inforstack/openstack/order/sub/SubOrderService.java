package com.inforstack.openstack.order.sub;

import java.util.List;

public interface SubOrderService {
	
	/**
	 * Create sub order by necessary fileds
	 * @param itemId
	 * @param orderId
	 * @param periodId
	 * @return
	 */
	public SubOrder createSubOrder(int itemId, String orderId, int periodId);
	
	/**
	 * find sub order by id
	 * @param subOrderId
	 * @return
	 */
	public SubOrder findSubOrderById(int subOrderId);
	
	/**
	 * find sub orders by order id and/or status
	 * @param orderId
	 * @param status
	 * @return
	 */
	public List<SubOrder> findSubOrders(String orderId, int status);
	
	/**
	 * delete sub order by id
	 * @param subOrderId
	 * @return
	 */
	public SubOrder deleteSubOrder(int subOrderId);
}
