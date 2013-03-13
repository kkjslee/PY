package com.inforstack.openstack.order;

import java.util.Date;
import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.controller.model.PaginationModel;

public interface OrderDao extends BasicDao<Order> {

	CursorResult<Integer> find(Integer tenantId, Date billingDate,
			Integer status);

	CursorResult<Integer> find(List<Integer> orderPeriods,
			Date billingDate, Integer status);

	public PaginationModel<Order> findWithCreator(int pageIndex, int pageSize, Integer tenantId,
			Integer status);

	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex, int pageSize);

}
