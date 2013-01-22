package com.inforstack.openstack.order;

public interface OrderDao {

	public void persist(Order order);
	
	public void merge(Order order);

}
