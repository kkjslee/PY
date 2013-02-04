package com.inforstack.openstack.order.sub;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface SubOrderDao extends BasicDao<SubOrder> {

	public List<SubOrder> find(String orderId, Integer status, Integer periodId);

}
