package com.inforstack.openstack.order;

import org.hibernate.ScrollableResults;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.controller.model.PaginationModel;

public interface OrderDao extends BasicDao<Order> {

	public ScrollableResults find(Integer tenantId, Integer status);

	public PaginationModel<Order> findWithCreator(int pageIndex, int pageSize, Integer tenantId,
			Integer status);

	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex, int pageSize);

}
