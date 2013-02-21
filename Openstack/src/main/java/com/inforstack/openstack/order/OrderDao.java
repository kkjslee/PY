package com.inforstack.openstack.order;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.controller.model.PaginationModel;

public interface OrderDao extends BasicDao<Order> {

	public List<Order> find(Integer tenantId, Integer status);

	public PaginationModel<Order> findWithCreator(int pageIndex, int pageSize, Integer tenantId,
			Integer status);

	public PaginationModel<Order> findAllWithoutSubOrder(int pageIndex, int pageSize);

}
