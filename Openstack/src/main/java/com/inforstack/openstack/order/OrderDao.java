package com.inforstack.openstack.order;

import java.util.List;

public interface OrderDao {

	public void persist(Order order);
	
	public void merge(Order order);

	public Order findById(String orderId);

	public List<Order> find(Integer tenantId, Integer status);

}
