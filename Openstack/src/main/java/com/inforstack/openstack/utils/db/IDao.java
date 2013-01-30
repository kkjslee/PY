package com.inforstack.openstack.utils.db;

import java.util.List;

public interface IDao<T> {

	public List<T> list();
	
	public T findById(Object primaryKey);
	
	public T findByObject(String name, Object value);
	
	public T persist(T instance);
	
	public void update(T instance);
	
	public void remove(T instance);
	
	public void remove(int id);
	
}
