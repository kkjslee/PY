package com.inforstack.openstack.item;

import java.util.List;

import com.inforstack.openstack.i18n.I18nLink;

public interface ItemService {
	
	public List<Category> listAllCategory(boolean excludeDisabled);
	
	public Category addCategory(I18nLink name, boolean enable);
	
	public void updateCategory(Category category);
	
	public void removeCategory(Category category);
	
	public ItemSpecification addItem(I18nLink name, float defaultPrice, int osType, String refId, boolean available, List<ItemMetadata> metadata);
	
}
