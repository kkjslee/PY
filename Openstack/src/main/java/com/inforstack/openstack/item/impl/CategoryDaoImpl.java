package com.inforstack.openstack.item.impl;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;

@Repository
public class CategoryDaoImpl extends BasicDaoImpl<Category> implements CategoryDao {

	@Override
	public Category findByName(String name) {
		return this.findByObject("name", name);
	}

}
