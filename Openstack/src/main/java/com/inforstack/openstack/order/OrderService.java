package com.inforstack.openstack.order;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.tenant.Tenant;

public interface OrderService {

	public Order createOrder(Order order);
	
	public Order createOrder(Tenant tenant, Date begin, Date end);
	
	public Order createOrder(Integer tenantId, Date begin, Date end);
	
	public Order changeOrderStatus(String orderId, int status);

	public Order findOrderById(String orderId);
	
	public List<Order> findAll(Integer tenantId, Integer status);
}
