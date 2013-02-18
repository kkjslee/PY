package com.inforstack.openstack.basic;

import java.util.List;

import com.inforstack.openstack.controller.model.PaginationModel;

public interface BasicDao<T> {

	public List<T> list();
	
	public T findById(Object primaryKey);
	
	public T findByObject(String name, Object value);
	
	public T persist(T instance);
	
	public void update(T instance);
	
	public void remove(T instance);

	PaginationModel<T> pagination(int pageIndex, int pageSize);
	
}
