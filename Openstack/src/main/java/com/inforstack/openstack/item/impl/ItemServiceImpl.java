package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;
import com.inforstack.openstack.item.ItemMetadata;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	
	private static final Log log = LogFactory.getLog(ItemServiceImpl.class);
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ItemSpecificationDao itemSpecificationDao;
	
	@Autowired
	private FlavorService flavorService;
	
	@Autowired
	private ImageService imageService;

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

	@Override
	public ItemSpecification addItem(String name, float defaultPrice, int osType, String refId, boolean available, List<ItemMetadata> metadata) {
		ItemSpecification newItem = null;
		String osTypeName = ItemSpecification.OS_TYPE_NONE;
		switch (osType) {
		case ItemSpecification.OS_TYPE_FLAVOR_ID:
			if (this.checkFlavor(refId)) {
				osTypeName = ItemSpecification.OS_TYPE_FLAVOR;
			} else {
				log.debug("Unknown flavor id: " + refId);
			}
			break;
		case ItemSpecification.OS_TYPE_IMAGE_ID:
			if (this.checkImage(refId)) {
				osTypeName = ItemSpecification.OS_TYPE_IMAGE;
			} else {
				log.debug("Unknown image id: " + refId);
			}
			break;
		case ItemSpecification.OS_TYPE_VOLUME_ID:
			osTypeName = ItemSpecification.OS_TYPE_VOLUME;
			break;
		case ItemSpecification.OS_TYPE_NETWORK_ID:
			osTypeName = ItemSpecification.OS_TYPE_NETWORK;
			break;
		default:
			log.debug("Unknown ItemSpecification Type: " + osType);
		}
		if (osTypeName != null) {
			Date now = new Date();
			newItem = new ItemSpecification();
			newItem.setName(name);
			newItem.setDefaultPrice(defaultPrice);
			newItem.setAvailable(available);
			newItem.setOsType(osTypeName);
			newItem.setRefId(refId);
			newItem.setCreated(now);
			newItem.setUpdated(now);
			if (metadata != null) {
				newItem.setMetadata(metadata);
			}
			newItem = this.itemSpecificationDao.persist(newItem);
		}
		return newItem;
	}
	
	private boolean checkFlavor(String id) {
		boolean success = false;
		try {
			Flavor[] flavors = this.flavorService.listFlavors();
			for (Flavor flavor : flavors) {
				if (flavor.getId().equalsIgnoreCase(id)) {
					success = true;
					break;
				}
			}
		} catch (OpenstackAPIException e) {
			log.error("Can't get the flavor list from openstack");
			throw new RuntimeException(e);
		}
		return success;
	}
	
	private boolean checkImage(String id) {
		boolean success = false;
		try {
			Image[] flavors = this.imageService.listImages();
			for (Image flavor : flavors) {
				if (flavor.getId().equalsIgnoreCase(id)) {
					success = true;
					break;
				}
			}
		} catch (OpenstackAPIException e) {
			log.error("Can't get the image list from openstack");
			throw new RuntimeException(e);
		}
		return success;
	}

}
