package com.inforstack.openstack.item;

import java.util.List;

public interface CategoryDao {
	
	public List<Category> list();
	
	public Category findById(int id);
	
	public Category findByName(String name);
	
	public Category persist(Category category);
	
	public void update(Category category);
	
	public void remove(Category category);
	
	public void remove(int id);

}
