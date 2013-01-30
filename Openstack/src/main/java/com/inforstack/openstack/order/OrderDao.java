package com.inforstack.openstack.order;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface OrderDao extends BasicDao<Order> {

	public List<Order> find(Integer tenantId, Integer status);

}
