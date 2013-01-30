package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;
import com.inforstack.openstack.utils.db.AbstractDao;

@Repository
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {

	@Override
	public Category findByName(String name) {
		return this.findByObject("name", name);
	}

}
