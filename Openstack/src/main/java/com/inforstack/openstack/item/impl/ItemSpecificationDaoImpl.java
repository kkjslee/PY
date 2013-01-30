package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;

@Repository
public class ItemSpecificationDaoImpl extends BasicDaoImpl<ItemSpecification> implements ItemSpecificationDao {

	@Override
	public ItemSpecification findByName(String name) {
		return this.findByObject("name", name);
	}

}
