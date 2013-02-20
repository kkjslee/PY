package com.inforstack.openstack.controller.admin;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.CategoryModel;
import com.inforstack.openstack.controller.model.I18nModel;
import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.controller.model.PriceModel;
import com.inforstack.openstack.controller.model.ProfileModel;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.i18n.lang.Language;
import com.inforstack.openstack.i18n.lang.LanguageService;
import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.Profile;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController {
	private static final Logger log = new Logger(ProductController.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private I18nService i18nService;

	@Autowired
	private LanguageService languageService;

	private final String ADMIN_PRODUCT_MODULE_HOME = "admin/modules/Product";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModuleForAdminCategory(Model model,
			HttpServletRequest request) {
		return ADMIN_PRODUCT_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
	public String bootstrapForAdminCategory(Model model) {
		model.addAttribute(Constants.PAGER_PAGE_INDEX,
				Constants.DEFAULT_PAGE_INDEX);
		model.addAttribute(Constants.PAGER_PAGE_SIZE,
				Constants.DEFAULT_PAGE_SIZE);
		List<Language> lList = new ArrayList<Language>();
		lList = languageService.list();
		model.addAttribute("lList", lList);
		return ADMIN_PRODUCT_MODULE_HOME + "/scripts/bootstrap";
	}

	@RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
	public String templateForAdminCategory(Model model) {
		List<Language> lList = new ArrayList<Language>();
		lList = languageService.list();
		model.addAttribute("lList", lList);
		return ADMIN_PRODUCT_MODULE_HOME + "/scripts/template";
	}

	@RequestMapping(value = "/listForJsp", method = RequestMethod.POST)
	public String listProducts(Model model, Integer pageIndex, Integer pageSize) {
		int pageIdx = -1;
		int pageSze = 0;
		if (pageIndex == null || pageIndex == 0) {
			log.info("no pageindex passed, set default value 1");
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
			log.info("no page size passed, set default value 20");
			pageSze = Constants.DEFAULT_PAGE_SIZE;
		} else {
			pageSze = pageSize;
		}
		Integer languageId = OpenstackUtil.getLanguage().getId();
		List<ItemSpecificationModel> models = new ArrayList<ItemSpecificationModel>();

		List<ItemSpecification> itemSpecifications = this.itemService
				.listAllItemSpecification();

		for (ItemSpecification itemSpecification : itemSpecifications) {

			// TODO:different between user and admin
			int nameId = itemSpecification.getNameId();
			List<I18nModel> itemNameList = new ArrayList<I18nModel>();
			I18nModel name = null;
			if (nameId > 0) {
				log.info("name ID:" + nameId);
				List<Language> lList = new ArrayList<Language>();
				lList = languageService.list();
				I18n i18n = null;
				for (Language l : lList) {
					i18n = i18nService.findByLinkAndLanguage(nameId, l.getId());
					if (i18n != null) {
						name = new I18nModel();
						name.setLanguageId(l.getId());
						name.setContent(i18n.getContent());
						itemNameList.add(name);
					}
				}

			} else {
				log.warn("itemSpecification nameid is null ,category id:"
						+ itemSpecification.getId());
			}
			I18nModel[] itemNameArray = new I18nModel[itemNameList.size()];
			itemNameList.toArray(itemNameArray);

			ItemSpecificationModel itemSpecificationModel = new ItemSpecificationModel();
			itemSpecificationModel.setId(itemSpecification.getId());
			itemSpecificationModel.setName(itemNameArray);
			itemSpecificationModel.setOsType(itemSpecification.getOsType());
			itemSpecificationModel.setRefId(itemSpecification.getRefId());
			List<Category> cList = itemSpecification.getCategories();
			CategoryModel[] cModelArray = new CategoryModel[cList.size()];
			for (int i = 0; i < cList.size(); i++) {
				Category c = cList.get(i);
				CategoryModel cModel = new CategoryModel();
				cModel.setEnable(c.getEnable());
				cModel.setSystem(c.getSystem());
				cModel.setId(c.getId());
				I18nModel[] cName = new I18nModel[1];
				cName[0] = new I18nModel();
				cName[0].setLanguageId(languageId);

				int cNameId = c.getNameId();
				if (cNameId > 0) {
					log.info("item name ID:" + cNameId);
					I18n i18n = null;
					i18n = i18nService.findByLinkAndLanguage(cNameId,
							languageId);
					if (i18n != null) {
						cName[0].setContent(i18n.getContent());
					}

				} else {
					log.warn("category name id is null ,item id:" + c.getId());
				}

				cModel.setName(cName);
				cModelArray[i] = cModel;
			}
			itemSpecificationModel.setCategories(cModelArray);
			itemSpecificationModel.setDefaultPrice(itemSpecification
					.getDefaultPrice());
			itemSpecificationModel.setAvailable(itemSpecification
					.getAvailable());
			itemSpecificationModel.setCreated(DateFormat.getDateTimeInstance(
					DateFormat.SHORT, DateFormat.SHORT,
					OpenstackUtil.getLocale()).format(
					itemSpecification.getCreated()));
			itemSpecificationModel.setUpdated(DateFormat.getDateTimeInstance(
					DateFormat.SHORT, DateFormat.SHORT,
					OpenstackUtil.getLocale()).format(
					itemSpecification.getUpdated()));

			Profile profile = itemSpecification.getProfile();
			if (profile != null) {
				ProfileModel profileModel = new ProfileModel();
				profileModel.setItem(itemSpecification.getId());
				if (profile.getCpu() != null) {
					profileModel.setCpu(profile.getCpu().getId());
				}
				if (profile.getMemory() != null) {
					profileModel.setMemory(profile.getMemory().getId());
				}
				if (profile.getDisk() != null) {
					profileModel.setDisk(profile.getDisk().getId());
				}
				if (profile.getNetwork() != null) {
					profileModel.setNetwork(profile.getNetwork().getId());
				}

				itemSpecificationModel.setProfile(profileModel);
			}

			models.add(itemSpecificationModel);
		}
		PagerModel<ItemSpecificationModel> page = new PagerModel<ItemSpecificationModel>(
				models, pageSze);
		models = page.getPagedData(pageIdx);
		model.addAttribute("pageIndex", pageIdx);
		model.addAttribute("pageSize", pageSze);
		model.addAttribute("pageTotal", page.getTotalRecord());
		model.addAttribute("dataList", models);
		return ADMIN_PRODUCT_MODULE_HOME + "/tr";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> createItemSpecification(Model model,
			@RequestBody ItemSpecificationModel itemSpecificationModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (itemSpecificationModel != null) {
			try {
				ItemSpecification itemSpecification = this.itemService
						.createItem(itemSpecificationModel);
				if (itemSpecification != null) {
					returnValue.put("success", "success");
				}
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
				returnValue.put("error", e.getMessage());
			}

		}
		return returnValue;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> updateItemSpecification(Model model,
			@RequestBody ItemSpecificationModel itemSpecificationModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (itemSpecificationModel != null) {
			try {
				this.itemService
						.updateItemSpecification(itemSpecificationModel);
				returnValue.put("success", "success");
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
				returnValue.put("error", e.getMessage());
			}

		}
		return returnValue;
	}

	@RequestMapping(value = "/editPrice", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> editPrice(Model model,
			@RequestBody PriceModel priceModel) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		if (priceModel != null) {
			try {
				this.itemService.updateItemSpecificationPrice(priceModel);
				returnValue.put("success", "success");
			} catch (ApplicationException e) {
				log.error(e.getMessage(), e);
				returnValue.put("error", e.getMessage());
			}

		}
		return returnValue;
	}
}
