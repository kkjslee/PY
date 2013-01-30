package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;
import com.inforstack.openstack.utils.db.AbstractDao;

@Repository
public class ItemSpecificationDaoImpl extends AbstractDao<ItemSpecification> implements ItemSpecificationDao {

	@Override
	public ItemSpecification findByName(String name) {
		return this.findByObject("name", name);
	}

}
