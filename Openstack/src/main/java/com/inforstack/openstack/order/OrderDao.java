package com.inforstack.openstack.order;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface OrderDao extends IDao<Order> {

	public List<Order> find(Integer tenantId, Integer status);

}
