package com.inforstack.openstack.item;

import java.util.List;

public interface ItemService {
	
	public List<Category> listAllCategory(boolean excludeDisabled);
	
	public Category addCategory(String name, boolean enable);
	
	public void updateCategory(Category category);
	
	public void removeCategory(Category category);
	
}
