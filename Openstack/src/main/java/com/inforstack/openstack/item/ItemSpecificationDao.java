package com.inforstack.openstack.item;

import com.inforstack.openstack.utils.db.IDao;

public interface ItemSpecificationDao extends IDao<ItemSpecification> {

	public ItemSpecification findByName(String name);
	
}
