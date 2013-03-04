package com.inforstack.openstack.basic;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import com.inforstack.openstack.controller.model.PaginationModel;

public interface BasicDao<T> {
	
	public List<T> listAll();
	
	public List<T> listByObject(String name, Object value);
	
	public T findById(Object primaryKey);
	
	public T findByObject(String name, Object value);
	
	public T persist(T instance);
	
	public void update(T instance);
	
	public void remove(T instance);

	public PaginationModel<T> pagination(int pageIndex, int pageSize, CriteriaQuery<T> query);

	public PaginationModel<T> pagination(int pageIndex, int pageSize);
	
}
