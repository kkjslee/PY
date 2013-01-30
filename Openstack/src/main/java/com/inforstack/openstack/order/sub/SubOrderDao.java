package com.inforstack.openstack.order.sub;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface SubOrderDao extends IDao<SubOrder> {

	public List<SubOrder> find(String orderId, Integer status);

}
