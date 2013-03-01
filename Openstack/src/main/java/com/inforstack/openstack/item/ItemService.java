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
	
	public com.inforstack.openstack.api.nova.flavor.Flavor getOpenStackFlavor(int dataCenterId, String itemId) throws ApplicationException;
	
	public List<com.inforstack.openstack.api.nova.flavor.Flavor> listOpenStackFlavor(int dataCenterId) throws ApplicationException;
	
	public void createFlavor(String name, int vcpus, int ram, int disk) throws ApplicationException;
	
	public void removeFlavor(String flavorId) throws ApplicationException;
	
	public com.inforstack.openstack.api.nova.image.Image getOpenStackImage(int dataCenterId, String itemId) throws ApplicationException;
	
	public com.inforstack.openstack.api.cinder.VolumeType getOpenStackVolumeType(int dataCenterId, String itemId) throws ApplicationException;
	
}
