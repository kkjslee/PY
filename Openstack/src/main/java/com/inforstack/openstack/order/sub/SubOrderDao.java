package com.inforstack.openstack.order.sub;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface SubOrderDao extends BasicDao<SubOrder> {

	public List<SubOrder> find(Integer orderId, List<Integer> statuses, List<Integer> orderPeriods);

	public SubOrder fetchOneByInstanceId(int id);

}
