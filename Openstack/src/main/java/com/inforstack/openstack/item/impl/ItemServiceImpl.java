package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;
import com.inforstack.openstack.item.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	
	//private static final Log log = LogFactory.getLog(ItemServiceImpl.class);
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> listAllCategory(boolean excludeDisabled) {
		List<Category> categories = this.categoryDao.list();
		if (excludeDisabled) {
			List<Category> list = new ArrayList<Category>();
			for (Category category : categories) {
				if (category.isEnable()) {
					list.add(category);
				}
			}
			categories = list;
		}
		return categories;
	}

	@Override
	public Category addCategory(String name, boolean enable) {
		Category category = new Category();
		category.setName(name);
		category.setEnable(enable);
		return this.categoryDao.persist(category);
	}

	@Override
	public void updateCategory(Category category) {
		this.categoryDao.update(category);
	}

	@Override
	public void removeCategory(Category category) {
		this.categoryDao.remove(category.getId());
	}

}
