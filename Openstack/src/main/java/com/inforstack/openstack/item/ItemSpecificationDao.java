package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface ItemSpecificationDao extends BasicDao<ItemSpecification> {

	public ItemSpecification findByName(String name);
	
}
