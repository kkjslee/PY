package com.inforstack.openstack.item;

import java.util.List;
import java.util.Map;

import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.controller.model.PriceModel;
import com.inforstack.openstack.exception.ApplicationException;

public interface ItemService {

	public List<Category> listAllCategory(boolean excludeDisabled);

	public Category getCategory(Integer id);

	public Category createCategory(CategoryModel model) throws ApplicationException;

	public void updateCategory(CategoryModel model) throws ApplicationException;

	public void removeCategory(Integer id) throws ApplicationException;

	public List<ItemSpecification> listItemSpecificationByCategory(Category category);

	public List<ItemSpecification> listAllItemSpecification();

	public ItemSpecification getItemSpecification(Integer id);
	
	public ItemSpecification getItemSpecificationFromRefId(String refId);
	
	public Map<String, String> getItemSpecificationDetail(Integer id);

	public ItemSpecification createItem(ItemSpecificationModel model) throws ApplicationException;

	public void updateItemSpecification(ItemSpecificationModel model) throws ApplicationException;

	public void updateItemSpecificationPrice(PriceModel model) throws ApplicationException;

	public void removeItemSpecification(Integer id) throws ApplicationException;

}
