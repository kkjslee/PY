package com.inforstack.openstack.order.sub;

import java.util.List;

public interface SubOrderService {
	
	public SubOrder createSubOrder(Integer itemId, String orderId, Integer periodId);
	
	public SubOrder findSubOrderById(Integer subOrderId);
	
	public List<SubOrder> findSubOrders(String orderId, int status);
	
	public SubOrder changeSubOrderStatus(Integer subOrderId, int status);
}
