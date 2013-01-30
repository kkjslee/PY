package com.inforstack.openstack.basic;

public interface BasicService<T> {
	
	public T findById(Object primaryKey);
	
	public T create(T instance);
	
	public T update(T instance);
	
	public T remove(T instance);
	
	public T removeByPrimaryKey(Object primaryKey);
}
