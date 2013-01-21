package com.inforstack.openstack.item;

import java.util.List;

import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.exception.ApplicationException;

public interface ItemService {
	
	public List<Category> listAllCategory(boolean excludeDisabled) ;
	
	public Category getCategory(int id);
	
	public Category createCategory(CategoryModel model) throws ApplicationException;
	
	public void updateCategory(CategoryModel model) throws ApplicationException;
	
	public void removeCategory(Integer id) throws ApplicationException;
	
	public List<ItemSpecification> listItemSpecificationByCategory(Category category);
	
	public ItemSpecification createItem(ItemSpecificationModel model, List<ItemMetadata> metadata) throws ApplicationException;
	
}
