package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.order.period.OrderPeriodService;
import com.inforstack.openstack.utils.Constants;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	private static final Logger log = new Logger(ItemServiceImpl.class);

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

	@Autowired
	private OrderPeriodService periodService;

	@Override
	public List<Category> listAllCategory(boolean excludeDisabled) {
		List<Category> list = new ArrayList<Category>();
		List<Category> categories = this.categoryDao.list();
		if (categories != null) {
			for (Category category : categories) {
				if (!excludeDisabled || category.getEnable()) {
					category.getName().getId();
					category.getName().getI18ns();
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
	public Category createCategory(CategoryModel model)
			throws ApplicationException {
		Category category = null;
		I18nModel[] i18nModels = model.getName();
		if (i18nModels != null && i18nModels.length > 0) {
			I18nLink link = this.i18nService.createI18n(
					i18nModels[0].getLanguageId(), i18nModels[0].getContent(),
					Constants.TABLE_CATEGORY, Constants.COLUMN_CATEGORY_NAME)
					.getI18nLink();
			if (link != null) {
				for (int idx = 1; idx < i18nModels.length; idx++) {
					this.i18nService.createI18n(
							i18nModels[idx].getLanguageId(),
							i18nModels[idx].getContent(), link);
				}
				category = new Category();
				category.setEnable(model.getEnable());
				category.setName(link);
				category.setSystem(false);
				category = this.categoryDao.persist(category);
			}
		}
		return category;
	}

	@Override
	public void updateCategory(CategoryModel model) throws ApplicationException {
		if (model.getId() != null) {
			Category category = this.categoryDao.findById(model.getId());
			if (category != null && !category.getSystem()) {
				Integer linkId = category.getName().getId();
				if (linkId != null) {
					I18nModel[] i18nModels = model.getName();
					for (I18nModel i18nModel : i18nModels) {
						I18n i18n = this.i18nService.findByLinkAndLanguage(
								linkId, i18nModel.getLanguageId());
						if (i18n == null) {
							this.i18nService.createI18n(
									i18nModel.getLanguageId(),
									i18nModel.getContent(), category.getName());
						} else {
							this.i18nService.updateI18n(linkId,
									i18nModel.getLanguageId(),
									i18nModel.getContent());
						}
					}
					category.setEnable(model.getEnable());
					this.categoryDao.update(category);
				}
			}
		}
	}

	@Override
	public void removeCategory(Integer id) throws ApplicationException {
		Category category = this.categoryDao.findById(id);
		if (category != null && !category.getSystem()) {
			this.categoryDao.remove(category);
		}
	}

	@Override
	public List<ItemSpecification> listItemSpecificationByCategory(
			Category category) {
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
		ItemSpecification itemSpecification = this.itemSpecificationDao
				.findById(id);
		if (itemSpecification != null) {
			itemSpecification.getName().getId();
			if (itemSpecification.getProfile() != null) {
				itemSpecification.getProfile().getId();
			}
		}
		return itemSpecification;
	}

	@Override
	public Map<String, String> getItemSpecificationDetail(Integer id) {
		HashMap<String, String> detail = new HashMap<String, String>();
		ItemSpecification itemSpecification = this.itemSpecificationDao
				.findById(id);
		if (itemSpecification != null) {
			int osType = itemSpecification.getOsType();
			String refId = itemSpecification.getRefId();
			switch (osType) {
			case ItemSpecification.OS_TYPE_FLAVOR_ID:
				try {
					Flavor flavor = this.flavorService.getFlavor(refId);
					detail.put("os_cpu", Integer.toString(flavor.getVcpus()));
					detail.put("os_memory", Integer.toString(flavor.getRam()));
					detail.put("os_disk", Integer.toString(flavor.getDisk()));
				} catch (OpenstackAPIException e) {
					log.debug("Unknown flavor id: " + refId);
				}
				break;
			case ItemSpecification.OS_TYPE_IMAGE_ID:
				try {
					Image image = this.imageService.getImage(refId);
					detail.put("os_imagename", image.getName());
				} catch (OpenstackAPIException e) {
					log.debug("Unknown image id: " + refId);
				}
				break;
			case ItemSpecification.OS_TYPE_PERIOD_ID:
				OrderPeriod orderPeriod = this.periodService
						.findPeriodById(Integer.valueOf(refId));
				detail.put("os_periodname", orderPeriod.getName()
						.getI18nContent());
				break;
			case ItemSpecification.OS_TYPE_VOLUME_ID:
				detail.put("os_size", Integer.toString(0));
				break;
			}
			List<ItemMetadata> metadataList = itemSpecification.getMetadata();
			for (ItemMetadata metadata : metadataList) {
				detail.put(metadata.getName().getI18nContent(), metadata
						.getValue().getI18nContent());
			}
		}
		return detail;
	}

	@Override
	public ItemSpecification createItem(ItemSpecificationModel model)
			throws ApplicationException {
		ItemSpecification newItem = null;
		String refId = model.getRefId();
		String osTypeName = ItemSpecification.OS_TYPE_NONE;
		if (model.getOsType() != null) {
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
			case ItemSpecification.OS_TYPE_PERIOD_ID:
				if (this.checkPeriod(refId)) {
					osTypeName = ItemSpecification.OS_TYPE_PERIOD;
				} else {
					log.debug("Unknown period id:" + refId);
				}
				break;
			case ItemSpecification.OS_TYPE_DATACENTER_ID:
				osTypeName = ItemSpecification.OS_TYPE_DATACENTER;
				break;
			default:
				log.debug("Unknown ItemSpecification Type: "
						+ model.getOsType());
			}
		} else {

		}
		if (osTypeName != null) {
			Date now = new Date();
			I18nModel[] i18nModels = model.getName();
			if (i18nModels != null && i18nModels.length > 0) {
				I18nLink link = this.i18nService.createI18n(
						i18nModels[0].getLanguageId(),
						i18nModels[0].getContent(),
						Constants.TABLE_ITEMSPECIFICATION,
						Constants.COLUMN_ITEMSPECIFICATION_NAME).getI18nLink();
				if (link != null) {
					for (int idx = 1; idx < i18nModels.length; idx++) {
						this.i18nService.createI18n(
								i18nModels[idx].getLanguageId(),
								i18nModels[idx].getContent(), link);
					}

					newItem = new ItemSpecification();
					newItem.setName(link);
					newItem.setDefaultPrice(model.getDefaultPrice());
					newItem.setAvailable(model.getAvailable());
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

							I18nLink nameLink = this.i18nService.createI18n(
									names[0].getLanguageId(),
									names[0].getContent(),
									Constants.TABLE_ITEMMETADATA,
									Constants.COLUMN_ITEMMETADATA_NAME)
									.getI18nLink();
							if (nameLink != null) {
								for (int idx = 1; idx < names.length; idx++) {
									this.i18nService.createI18n(
											names[idx].getLanguageId(),
											names[idx].getContent(), nameLink);
								}
							}

							I18nLink valueLink = this.i18nService.createI18n(
									values[0].getLanguageId(),
									values[0].getContent(),
									Constants.TABLE_ITEMMETADATA,
									Constants.COLUMN_ITEMMETADATA_VALUE)
									.getI18nLink();
							if (valueLink != null) {
								for (int idx = 1; idx < values.length; idx++) {
									this.i18nService
											.createI18n(
													values[idx].getLanguageId(),
													values[idx].getContent(),
													valueLink);
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
							Category category = this.categoryDao
									.findById(categoryModel.getId());
							if (category != null) {
								categorys.add(category);
							}
						}
						newItem.setCategories(categorys);
					}

					ProfileModel profileModel = model.getProfile();
					if (profileModel != null) {
						Profile profile = new Profile();
						if (profileModel.getCpu() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getCpu());
							if (item != null) {
								profile.setCpu(item);
							}
						}
						if (profileModel.getMemory() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getMemory());
							if (item != null) {
								profile.setMemory(item);
							}
						}
						if (profileModel.getDisk() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getDisk());
							if (item != null) {
								profile.setDisk(item);
							}
						}
						if (profileModel.getNetwork() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getNetwork());
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
	public void updateItemSpecification(ItemSpecificationModel model)
			throws ApplicationException {
		if (model.getId() != null) {
			ItemSpecification itemSpecification = this.itemSpecificationDao
					.findById(model.getId());
			if (itemSpecification != null) {
				Integer linkId = itemSpecification.getName().getId();
				if (linkId != null) {
					I18nModel[] i18nModels = model.getName();
					for (I18nModel i18nModel : i18nModels) {
						I18n i18n = this.i18nService.findByLinkAndLanguage(
								linkId, i18nModel.getLanguageId());
						if (i18n == null) {
							this.i18nService.createI18n(
									i18nModel.getLanguageId(),
									i18nModel.getContent(),
									itemSpecification.getName());
						} else {
							this.i18nService.updateI18n(linkId,
									i18nModel.getLanguageId(),
									i18nModel.getContent());
						}
					}

					Date now = new Date();

					itemSpecification.setDefaultPrice(model.getDefaultPrice());
					itemSpecification.setAvailable(model.getAvailable());
					/*
					 * itemSpecification.setOsType(model.getOsType());
					 * itemSpecification.setRefId(model.getRefId());
					 */
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
							Category category = this.categoryDao
									.findById(categoryModel.getId());
							if (category != null) {
								categorys.add(category);
							}
						}
						itemSpecification.setCategories(categorys);
					}

					ProfileModel profileModel = model.getProfile();
					if (profileModel != null) {
						Profile profile = itemSpecification.getProfile();
						if (profile == null) {
							profile = new Profile();
						}
						if (profileModel.getCpu() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getCpu());
							if (item != null) {
								profile.setCpu(item);
							}
						}
						if (profileModel.getMemory() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getMemory());
							if (item != null) {
								profile.setMemory(item);
							}
						}
						if (profileModel.getDisk() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getDisk());
							if (item != null) {
								profile.setDisk(item);
							}
						}
						if (profileModel.getNetwork() != null) {
							ItemSpecification item = this.itemSpecificationDao
									.findById(profileModel.getNetwork());
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
	public void updateItemSpecificationPrice(PriceModel model)
			throws ApplicationException {
		if (model.getItemSpecificationId() != null && model.getValue() != null) {
			ItemSpecification itemSpecification = this.itemSpecificationDao
					.findById(model.getItemSpecificationId());
			float priceValue = itemSpecification.getDefaultPrice();
			if (priceValue != model.getValue().floatValue()) {
				Price price = new Price();
				price.setItemSpecification(itemSpecification);
				price.setValue(model.getValue().floatValue());
				Date now = new Date();
				price.setCreated(now);
				if (model.getActivated() != null) {
					price.setActivated(model.getActivated());
				} else {
					price.setActivated(now);
				}

				List<Price> prices = itemSpecification.getPrices();
				prices.add(price);
				itemSpecification.setPrices(prices);
				// TODO: a new price not default price
				itemSpecification
						.setDefaultPrice(model.getValue().floatValue());

				this.itemSpecificationDao.update(itemSpecification);
			}
		}
	}

	@Override
	public void removeItemSpecification(Integer id) throws ApplicationException {
		ItemSpecification itemSpecification = this.itemSpecificationDao
				.findById(id);
		if (itemSpecification != null) {
			this.itemSpecificationDao.remove(itemSpecification);
		}
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

	private boolean checkPeriod(String id) {
		boolean success = (this.periodService.findPeriodById(Integer
				.parseInt(id)) != null);
		return success;
	}

	/**
	 * 
	 */
	@Override
	public List<ItemSpecification> listAllItemSpecification() {
		List<ItemSpecification> list = new ArrayList<ItemSpecification>();
		list = itemSpecificationDao.list();
		for (ItemSpecification itemSpecification : list) {
			List<Category> categories = itemSpecification.getCategories();
			for (Category category : categories) {
				category.getId();
			}
			itemSpecification.getName().getId();
			itemSpecification.getName().getI18ns();
		}
		return list;
	}

}
