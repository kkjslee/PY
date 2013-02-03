package com.inforstack.openstack.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.ItemSpecificationModel;
import com.inforstack.openstack.controller.model.PriceModel;
import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController {
  private static final Logger log = new Logger(ProductController.class);

  @Autowired
  private ItemService itemService;

  private final String ADMIN_PRODUCT_MODULE_HOME = "admin/modules/Product";

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModuleForAdminCategory(Model model, HttpServletRequest request) {
    return ADMIN_PRODUCT_MODULE_HOME + "/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrapForAdminCategory(Model model) {
    model.addAttribute(Constants.PAGER_PAGE_INDEX, Constants.DEFAULT_PAGE_INDEX);
    model.addAttribute(Constants.PAGER_PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
    return ADMIN_PRODUCT_MODULE_HOME + "/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String templateForAdminCategory(Model model) {
    return ADMIN_PRODUCT_MODULE_HOME + "/scripts/template";
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> createItemSpecification(Model model,
      ItemSpecificationModel itemSpecificationModel) {
    Map<String, Object> returnValue = new HashMap<String, Object>();
    if (itemSpecificationModel != null) {
      try {
        ItemSpecification itemSpecification = this.itemService.createItem(itemSpecificationModel);
        if (itemSpecification != null) {
          returnValue.put("success", "success");
        }
      } catch (ApplicationException e) {
        log.error(e.getMessage(), e);
      }

    }
    return returnValue;
  }

  @RequestMapping(value = "/editPrice", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> editPrice(Model model, PriceModel priceModel) {
    Map<String, Object> returnValue = new HashMap<String, Object>();
    if (priceModel != null) {
      try {
        this.itemService.updateItemSpecificationPrice(priceModel);
        returnValue.put("success", "success");
      } catch (ApplicationException e) {
        log.error(e.getMessage(), e);
      }

    }
    return returnValue;
  }
}
