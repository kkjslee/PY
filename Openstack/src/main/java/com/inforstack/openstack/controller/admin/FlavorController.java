package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.controller.model.FlavorModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/flavor")
public class FlavorController {

  @Autowired
  private FlavorService flavorService;

  @Autowired
  private Validator validator;

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModule(Model model, HttpServletRequest request) {
    return "admin/modules/Flavor/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrap(Model model) {
    return "admin/modules/Flavor/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String template(Model model) {
    // retrieve images and flavors
    return "admin/modules/Flavor/scripts/template";
  }

  @RequestMapping(value = "/getPagerFlavorList", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<FlavorModel> getPagerFlavors(Model model, int pageIndex, int pageSize) {
    List<FlavorModel> flavorList = new ArrayList<FlavorModel>();
    try {
      Flavor[] flavors = flavorService.listFlavors();
      FlavorModel flavorModel = null;
      for (Flavor flavor : flavors) {
        if (ValidateUtil.checkValidFlavor(flavor)) {
          flavorModel = new FlavorModel();
          flavorModel.setFlavorId(flavor.getId());
          flavorModel.setDisk(flavor.getDisk());
          flavorModel.setFlavorName(flavor.getName());
          flavorModel.setRam(flavor.getRam());
          flavorModel.setVcpus(flavor.getVcpus());
          flavorList.add(flavorModel);
        }

      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }
    PagerModel<FlavorModel> page = new PagerModel<FlavorModel>(flavorList, pageSize);
    flavorList = page.getPagedData(pageIndex);
    model.addAttribute("pageIndex", pageIndex);
    model.addAttribute("pageSize", pageSize);
    return flavorList;
  }

  @RequestMapping(value = "/createFlavor", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> createFlavor(Model model, FlavorModel flavorModel) {
    Map<String, Object> ret = new HashMap<String, Object>();
    String errorMsg = ValidateUtil.validModel(validator, "admin", flavorModel);
    if (errorMsg != null) {
      ret.put(Constants.JSON_ERROR_STATUS, errorMsg);
      return ret;
    }
    try {
      flavorService.createFlavor(flavorModel.getFlavorName(), flavorModel.getVcpus(),
          flavorModel.getRam(), flavorModel.getDisk());
    } catch (OpenstackAPIException e) {
      ret.put(Constants.JSON_ERROR_STATUS, e.getMessage());
    }
    ret.put(Constants.JSON_SUCCESS_STATUS, "success");
    return ret;
  }

  @RequestMapping(value = "/removeFlavor", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String createVM(Model model, String flavorId) {
    if (StringUtils.isNullOrEmpty(flavorId)) {
      return Constants.JSON_STATUS_FAILED;
    }
    try {
      Flavor flavor = new Flavor();
      flavor.setId(flavorId);
      flavorService.removeFlavor(flavor);
    } catch (OpenstackAPIException e) {
      return Constants.JSON_STATUS_EXCEPTION;
    }
    return Constants.JSON_STATUS_DONE;
  }
}
