package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/flavor")
public class FlavorController {

  private static final Logger log = new Logger(FlavorController.class);

  @Autowired
  private FlavorService flavorService;

  @Autowired
  private Validator validator;

  private final String FLAVOR_MODULE_HOME = "admin/modules/Flavor";

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModule(Model model, HttpServletRequest request) {
    return FLAVOR_MODULE_HOME + "/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrap(Model model) {
    model.addAttribute(Constants.PAGER_PAGE_INDEX, Constants.DEFAULT_PAGE_INDEX);
    model.addAttribute(Constants.PAGER_PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
    return FLAVOR_MODULE_HOME + "/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String template(Model model) {
    return FLAVOR_MODULE_HOME + "/scripts/template";
  }

  @RequestMapping(value = "/getPagerFlavorList", method = RequestMethod.POST)
  public String getPagerFlavors(Model model, Integer pageIndex, Integer pageSize) {
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
    getPagerFlavors(model, pageIdx, pageSze, true);
    return FLAVOR_MODULE_HOME + "/tr";
  }

  @RequestMapping(value = "/getPagerAllFlavorList", method = RequestMethod.POST)
  public String getPagerAllFlavors(Model model, Integer pageIndex, Integer pageSize) {
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
    getPagerFlavors(model, pageIdx, pageSze, false);
    return FLAVOR_MODULE_HOME + "/tr";
  }

  // this is for instance selection
  @RequestMapping(value = "/flavorList", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<FlavorModel> listFlavors(Model model) {
    List<FlavorModel> flavorModels = getAllFlavors(true);
    return flavorModels;
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
      ret.put(Constants.JSON_SUCCESS_STATUS, "success");
    } catch (OpenstackAPIException e) {
      ret.put(Constants.JSON_ERROR_STATUS, e.getMessage());
    }
    return ret;
  }

  @RequestMapping(value = "/getFlavorDetails", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  FlavorModel getFlavor(Model model, String flavorId) {
    if (StringUtil.isNullOrEmpty(flavorId, true)) {
      return null;
    }
    Flavor flavor = null;
    FlavorModel fModel = new FlavorModel();
    try {
      flavor = flavorService.getFlavor(flavorId);
    } catch (OpenstackAPIException e) {
      log.error("flavor id:" + flavorId + e.getMessage(), e);
    }
    if (flavor == null) {
      log.warn("flavor id:" + flavorId + " IS NULL ");
    } else {
      fModel.setFlavorId(flavorId);
      fModel.setFlavorName(flavor.getName());
      fModel.setRam(flavor.getRam());
      fModel.setVcpus(flavor.getVcpus());
    }
    return fModel;
  }

  @RequestMapping(value = "/removeFlavor", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String removeFlavor(Model model, String flavorId) {
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

  /**
   * check all flavor unique name
   * 
   * @return if exist return false
   */
  @RequestMapping(value = "nameCheck", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String checkUniqueName(String name) {
    if (StringUtil.isNullOrEmpty(name, true)) {
      return Constants.JSON_STATUS_FALSE;
    }
    if (flavorNameExist(name)) {
      return Constants.JSON_STATUS_FALSE;
    } else {
      return Constants.JSON_STATUS_TRUE;
    }
  }

  private List<FlavorModel> getPagerFlavors(Model model, int pageIdx, int pageSze, boolean needCheck) {
    List<FlavorModel> flavorList = new ArrayList<FlavorModel>();
    try {
      Flavor[] flavors = flavorService.listFlavors();
      if (flavors != null) {
        FlavorModel flavorModel = null;
        for (Flavor flavor : flavors) {
          flavorModel = new FlavorModel();
          flavorModel.setFlavorId(flavor.getId());
          flavorModel.setDisk(flavor.getDisk());
          flavorModel.setFlavorName(flavor.getName());
          flavorModel.setRam(flavor.getRam());
          flavorModel.setVcpus(flavor.getVcpus());
          if (!flavor.isDisabled()) {
            flavorModel.setStatus(OpenstackUtil.getMessage("admin.flavor.enabled.status"));
          } else {
            flavorModel.setStatus(OpenstackUtil.getMessage("admin.flavor.disbled.status"));
          }
          if (needCheck) {
            if (!ValidateUtil.checkValidFlavor(flavor)) {
              continue;
            }
          }
          flavorList.add(flavorModel);
        }

        PagerModel<FlavorModel> page = new PagerModel<FlavorModel>(flavorList, pageSze);
        flavorList = page.getPagedData(pageIdx);
        model.addAttribute("pageIndex", pageIdx);
        model.addAttribute("pageSize", pageSze);
        model.addAttribute("pageTotal", page.getTotalRecord());
        model.addAttribute("dataList", flavorList);
      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }
    return flavorList;
  }

  // has no pager
  private List<FlavorModel> getAllFlavors(boolean needCheck) {
    List<FlavorModel> flavorModels = new ArrayList<FlavorModel>();
    try {
      Flavor[] flavors = flavorService.listFlavors();
      FlavorModel flavorModel = null;
      for (Flavor flavor : flavors) {
        flavorModel = new FlavorModel();
        flavorModel.setFlavorId(flavor.getId());
        flavorModel.setDisk(flavor.getDisk());
        flavorModel.setFlavorName(flavor.getName());
        flavorModel.setRam(flavor.getRam());
        flavorModel.setVcpus(flavor.getVcpus());
        if (needCheck) {
          if (!ValidateUtil.checkValidFlavor(flavor)) {
            continue;
          }
        }
        flavorModels.add(flavorModel);
      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }
    return flavorModels;
  }

  private boolean flavorNameExist(String name) {
    if (StringUtil.isNullOrEmpty(name, true)) {
      return false;
    }
    List<FlavorModel> flavorList = getAllFlavors(false);
    for (FlavorModel m : flavorList) {
      if (m.getFlavorName().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }
}
