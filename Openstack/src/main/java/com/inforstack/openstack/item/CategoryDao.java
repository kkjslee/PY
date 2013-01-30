package com.inforstack.openstack.item;

import com.inforstack.openstack.basic.BasicDao;

public interface CategoryDao extends BasicDao<Category> {
	
	public Category findByName(String name);

}
