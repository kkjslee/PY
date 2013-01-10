package com.inforstack.openstack.item;

import java.util.List;

public interface ItemSpecificationDao {

	public List<ItemSpecification> list();
	
	public ItemSpecification findById(int id);
	
	public ItemSpecification findByName(String name);
	
	public ItemSpecification persist(ItemSpecification itemSpecification);
	
	public void update(ItemSpecification itemSpecification);
	
	public void remove(ItemSpecification itemSpecification);
	
	public void remove(int id);
	
}
