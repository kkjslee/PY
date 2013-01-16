package com.inforstack.openstack.item;

import java.util.List;

public interface ItemService {
	
	public List<Category> listAllCategory(boolean excludeDisabled);
	
	public Category addCategory(int languageId, String name, boolean enable);
	
	public void updateCategory(Category category, int languageId, String name, boolean enable);
	
	public void removeCategory(Category category);
	
	public ItemSpecification addItem(int languageId, String name, float defaultPrice, int osType, String refId, boolean available, List<ItemMetadata> metadata);
	
}
