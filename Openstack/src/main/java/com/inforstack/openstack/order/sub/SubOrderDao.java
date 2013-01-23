package com.inforstack.openstack.order.sub;

import java.util.List;

public interface SubOrderDao {

	public void persist(SubOrder subOrder);

	public List<SubOrder> find(String orderId, Integer status);

	public SubOrder findById(Integer subOrderId);

}
