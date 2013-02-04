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
  private LanguageService languageService;

  private final String ADMIN_PRODUCT_MODULE_HOME = "admin/modules/Product";

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModuleForAdminCategory(Model model, HttpServletRequest request) {
    return ADMIN_PRODUCT_MODULE_HOME + "/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrapForAdminCategory(Model model) {
    model.addAttribute(Constants.PAGER_PAGE_INDEX, Constants.DEFAULT_PAGE_INDEX);
    model.addAttribute(Constants.PAGER_PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
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
    List<Category> categories = this.itemService.listAllCategory(false);
    List<ItemSpecificationModel> models = new ArrayList<ItemSpecificationModel>();
    for (Category category : categories) {
      I18nModel[] name = new I18nModel[1];
      name[0] = new I18nModel();
      name[0].setLanguageId(languageId);
      name[0].setContent(category.getName().getI18nContent());

      CategoryModel cm = new CategoryModel();
      cm.setId(category.getId());
      cm.setName(name);
      cm.setEnable(category.getEnable());

      List<ItemSpecification> itemSpecifications = this.itemService
          .listItemSpecificationByCategory(category);

      for (ItemSpecification itemSpecification : itemSpecifications) {
        I18nModel[] itemName = new I18nModel[1];
        itemName[0] = new I18nModel();
        itemName[0].setLanguageId(languageId);
        itemName[0].setContent(itemSpecification.getName().getI18nContent());

        ItemSpecificationModel itemSpecificationModel = new ItemSpecificationModel();
        itemSpecificationModel.setId(itemSpecification.getId());
        itemSpecificationModel.setName(itemName);
        itemSpecificationModel.setOsType(itemSpecification.getOsType());
        itemSpecificationModel.setRefId(itemSpecification.getRefId());
        itemSpecificationModel.setDefaultPrice(itemSpecification.getDefaultPrice());
        itemSpecificationModel.setAvailable(itemSpecification.getAvailable());
        itemSpecificationModel.setCreated(DateFormat.getDateTimeInstance(DateFormat.SHORT,
            DateFormat.SHORT, OpenstackUtil.getLocale()).format(itemSpecification.getCreated()));
        itemSpecificationModel.setUpdated(DateFormat.getDateTimeInstance(DateFormat.SHORT,
            DateFormat.SHORT, OpenstackUtil.getLocale()).format(itemSpecification.getUpdated()));

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
    }
    PagerModel<ItemSpecificationModel> page = new PagerModel<ItemSpecificationModel>(models,
        pageSze);
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
        ItemSpecification itemSpecification = this.itemService.createItem(itemSpecificationModel);
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

  @RequestMapping(value = "/editPrice", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> editPrice(Model model, @RequestBody PriceModel priceModel) {
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