package com.inforstack.openstack.item;

import com.inforstack.openstack.utils.db.IDao;

public interface CategoryDao extends IDao<Category> {
	
	public Category findByName(String name);

}
