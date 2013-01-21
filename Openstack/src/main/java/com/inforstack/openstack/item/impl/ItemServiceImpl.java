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
import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.controller.model.ItemMetadataModel;
import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.controller.model.PriceModel;
import com.inforstack.openstack.controller.model.ProfileModel;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.i18n.link.I18nLinkService;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;
import com.inforstack.openstack.item.ItemMetadata;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;
import com.inforstack.openstack.item.Price;
import com.inforstack.openstack.item.Profile;
import com.inforstack.openstack.utils.Constants;

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
	
	@Autowired
	private I18nService i18nService;
	
	@Autowired
	private I18nLinkService i18nLinkService;

	@Override
	public List<Category> listAllCategory(boolean excludeDisabled) {
		List<Category> list = new ArrayList<Category>();
		List<Category> categories = this.categoryDao.list();
		if (categories != null) {
			for (Category category : categories) {
				if (!excludeDisabled || category.getEnable()) {
					category.getName().getId();
					list.add(category);
				}
			}
		}
		return categories;
	}
	
	@Override
	public Category getCategory(Integer id) {
		Category category = this.categoryDao.findById(id);
		if (category != null) {
			category.getName().getId();
		}
		return category;
	}

	@Override
	public Category createCategory(CategoryModel model) throws ApplicationException {
		Category category = null;
		I18nModel[] i18nModels = model.getName();
		if (i18nModels != null && i18nModels.length > 0) {
			I18nLink link = this.i18nService.createI18n(i18nModels[0].getLanguageId(), i18nModels[0].getContent(), Constants.TABLE_CATEGORY, Constants.COLUMN_CATEGORY_NAME).getI18nLink();
			if (link != null) {
				for (int idx = 1; idx < i18nModels.length; idx++) {
					this.i18nService.createI18n(i18nModels[idx].getLanguageId(), i18nModels[idx].getContent(), link);
				}
				category = new Category();
				category.setEnable(model.isEnable());
				category.setName(link);
				category = this.categoryDao.persist(category);
			}
		}
		return category;
	}

	@Override
	public void updateCategory(CategoryModel model) throws ApplicationException {
		if (model.getId() != null) {
			Category category = this.categoryDao.findById(model.getId());
			if (category != null) {
				Integer linkId = category.getName().getId();
				if (linkId != null) {
					I18nModel[] i18nModels = model.getName();
					for (I18nModel i18nModel : i18nModels) {
						I18n i18n = this.i18nService.findByLinkAndLanguage(linkId, i18nModel.getLanguageId());
						if (i18n == null) {
							this.i18nService.createI18n(i18nModel.getLanguageId(), i18nModel.getContent(), category.getName());
						} else {
							this.i18nService.updateI18n(linkId, i18nModel.getLanguageId(), i18nModel.getContent());
						}
					}
					category.setEnable(model.isEnable());
					this.categoryDao.update(category);
				}
			}
		}
	}

	@Override
	public void removeCategory(Integer id) throws ApplicationException {
		this.categoryDao.remove(id);
	}
	
	@Override
	public List<ItemSpecification> listItemSpecificationByCategory(Category category) {
		List<ItemSpecification> list = null;
		Category c = this.categoryDao.findById(category.getId());
		if (c != null) {
			list = c.getItemSpecifications();
			for (ItemSpecification is : list) {
				is.getName().getId();
				if (is.getProfile() != null) {
					is.getProfile().getId();
				}
			}
		}
		return list;
	}

	@Override
	public ItemSpecification getItemSpecification(Integer id) {
		ItemSpecification itemSpecification = this.itemSpecificationDao.findById(id);
		if (itemSpecification != null) {
			itemSpecification.getName().getId();
			if (itemSpecification.getProfile() != null) {
				itemSpecification.getProfile().getId();
			}
		}
		return itemSpecification;
	}
	
	@Override
	public ItemSpecification createItem(ItemSpecificationModel model) throws ApplicationException {
		ItemSpecification newItem = null;
		String refId = model.getRefId();
		String osTypeName = ItemSpecification.OS_TYPE_NONE;
		switch (model.getOsType()) {
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
		case ItemSpecification.OS_TYPE_USAGE_ID:
			osTypeName = ItemSpecification.OS_TYPE_USAGE;
			break;
		default:
			log.debug("Unknown ItemSpecification Type: " + model.getOsType());
		}
		if (osTypeName != null) {
			Date now = new Date();
			I18nModel[] i18nModels = model.getName();
			if (i18nModels != null && i18nModels.length > 0) {
				I18nLink link = this.i18nService.createI18n(i18nModels[0].getLanguageId(), i18nModels[0].getContent(), Constants.TABLE_ITEMSPECIFICATION, Constants.COLUMN_ITEMSPECIFICATION_NAME).getI18nLink();
				if (link != null) {
					for (int idx = 1; idx < i18nModels.length; idx++) {
						this.i18nService.createI18n(i18nModels[idx].getLanguageId(), i18nModels[idx].getContent(), link);
					}
					
					newItem = new ItemSpecification();
					newItem.setName(link);
					newItem.setDefaultPrice(model.getDefaultPrice());
					newItem.setAvailable(model.isAvailable());
					newItem.setOsType(model.getOsType());
					newItem.setRefId(refId);
					newItem.setCreated(now);
					newItem.setUpdated(now);
					
					ItemMetadataModel[] metadataModel = model.getMetadata();
					if (metadataModel != null) {
						ArrayList<ItemMetadata> metadataList = new ArrayList<ItemMetadata>();
						for (ItemMetadataModel m : metadataModel) {
							I18nModel[] names = m.getName();
							I18nModel[] values = m.getValue();
							
							I18nLink nameLink = this.i18nService.createI18n(names[0].getLanguageId(), names[0].getContent(), Constants.TABLE_ITEMMETADATA, Constants.COLUMN_ITEMMETADATA_NAME).getI18nLink();
							if (nameLink != null) {
								for (int idx = 1; idx < names.length; idx++) {
									this.i18nService.createI18n(names[idx].getLanguageId(), names[idx].getContent(), nameLink);
								}
							}
							
							I18nLink valueLink = this.i18nService.createI18n(values[0].getLanguageId(), values[0].getContent(), Constants.TABLE_ITEMMETADATA, Constants.COLUMN_ITEMMETADATA_VALUE).getI18nLink();
							if (valueLink != null) {
								for (int idx = 1; idx < values.length; idx++) {
									this.i18nService.createI18n(values[idx].getLanguageId(), values[idx].getContent(), valueLink);
								}
							}
							
							ItemMetadata metadata = new ItemMetadata();
							metadata.setName(nameLink);
							metadata.setValue(valueLink);
							metadata.setItemSpecification(newItem);
							
							metadataList.add(metadata);
						}
						newItem.setMetadata(metadataList);
					}
					
					Price price = new Price();
					price.setItemSpecification(newItem);
					price.setValue(model.getDefaultPrice());
					price.setCreated(now);
					price.setActivated(now);
					
					ArrayList<Price> prices = new ArrayList<Price>();
					prices.add(price);
					newItem.setPrices(prices);
					
					CategoryModel[] categoryModels = model.getCategories();
					if (categoryModels != null) {
						ArrayList<Category> categorys = new ArrayList<Category>();						
						for (CategoryModel categoryModel : categoryModels) {
							Category category = this.categoryDao.findById(categoryModel.getId());
							if (category != null) {
								categorys.add(category);
							}
						}
						newItem.setCategories(categorys);
					}
					
					ProfileModel profileModel =  model.getProfile();
					if (profileModel != null) {
						Profile profile = new Profile();
						if (profileModel.getCpuId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getCpuId());
							if (item != null) {
								profile.setCpu(item);
							}
						}
						if (profileModel.getMemoryId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getMemoryId());
							if (item != null) {
								profile.setMemory(item);
							}
						}
						if (profileModel.getDiskId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getDiskId());
							if (item != null) {
								profile.setDisk(item);
							}
						}
						if (profileModel.getNetworkId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getNetworkId());
							if (item != null) {
								profile.setNetwork(item);
							}
						}
						newItem.setProfile(profile);
					}
					
					newItem = this.itemSpecificationDao.persist(newItem);
				}
			}
			
		}
		return newItem;
	}

	@Override
	public void updateItemSpecification(ItemSpecificationModel model) throws ApplicationException {
		if (model.getId() != null) {
			ItemSpecification itemSpecification = this.itemSpecificationDao.findById(model.getId());
			if (itemSpecification != null) {
				Integer linkId = itemSpecification.getName().getId();
				if (linkId != null) {
					I18nModel[] i18nModels = model.getName();
					for (I18nModel i18nModel : i18nModels) {
						I18n i18n = this.i18nService.findByLinkAndLanguage(linkId, i18nModel.getLanguageId());
						if (i18n == null) {
							this.i18nService.createI18n(i18nModel.getLanguageId(), i18nModel.getContent(), itemSpecification.getName());
						} else {
							this.i18nService.updateI18n(linkId, i18nModel.getLanguageId(), i18nModel.getContent());
						}
					}

					Date now = new Date();
					
					itemSpecification.setDefaultPrice(model.getDefaultPrice());
					itemSpecification.setAvailable(model.isAvailable());
					itemSpecification.setOsType(model.getOsType());
					itemSpecification.setRefId(model.getRefId());
					itemSpecification.setUpdated(now);
					
					float defaultPrice = model.getDefaultPrice();
					if (defaultPrice != itemSpecification.getDefaultPrice()) {
						Price price = new Price();
						price.setItemSpecification(itemSpecification);
						price.setValue(defaultPrice);
						price.setCreated(now);
						price.setActivated(now);
						
						List<Price> prices = itemSpecification.getPrices();
						prices.add(price);
						itemSpecification.setPrices(prices);
					}
					
					CategoryModel[] categoryModels = model.getCategories();
					if (categoryModels != null) {
						ArrayList<Category> categorys = new ArrayList<Category>();						
						for (CategoryModel categoryModel : categoryModels) {
							Category category = this.categoryDao.findById(categoryModel.getId());
							if (category != null) {
								categorys.add(category);
							}
						}
						itemSpecification.setCategories(categorys);
					}
					
					ProfileModel profileModel =  model.getProfile();
					if (profileModel != null) {
						Profile profile = itemSpecification.getProfile();
						if (profile == null) {
							profile = new Profile();
						}
						if (profileModel.getCpuId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getCpuId());
							if (item != null) {
								profile.setCpu(item);
							}
						}
						if (profileModel.getMemoryId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getMemoryId());
							if (item != null) {
								profile.setMemory(item);
							}
						}
						if (profileModel.getDiskId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getDiskId());
							if (item != null) {
								profile.setDisk(item);
							}
						}
						if (profileModel.getNetworkId() != null) {
							ItemSpecification item = this.itemSpecificationDao.findById(profileModel.getNetworkId());
							if (item != null) {
								profile.setNetwork(item);
							}
						}
						itemSpecification.setProfile(profile);
					}
					
					this.itemSpecificationDao.update(itemSpecification);
				}
			}
		}
	}
	
	@Override
	public void updateItemSpecificationPrice(PriceModel model) throws ApplicationException {
		if (model.getItemSpecificationId() != null && model.getValue() != null && model.getActivated() != null) {
			ItemSpecification itemSpecification = this.itemSpecificationDao.findById(model.getItemSpecificationId());
			float priceValue = itemSpecification.getDefaultPrice();
			if (priceValue != model.getValue().floatValue()) {
				Price price = new Price();
				price.setItemSpecification(itemSpecification);
				price.setValue(model.getValue().floatValue());
				price.setCreated(new Date());
				price.setActivated(model.getActivated());
				
				List<Price> prices = itemSpecification.getPrices();
				prices.add(price);
				itemSpecification.setPrices(prices);
				
				this.itemSpecificationDao.update(itemSpecification);
			}
		}
	}

	@Override
	public void removeItemSpecification(Integer id) throws ApplicationException {
		this.itemSpecificationDao.remove(id);
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
