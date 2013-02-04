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
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.server.Address;
import com.inforstack.openstack.api.nova.server.Addresses;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerAction;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.api.nova.server.impl.PauseServer;
import com.inforstack.openstack.api.nova.server.impl.ResumeServer;
import com.inforstack.openstack.api.nova.server.impl.StartServer;
import com.inforstack.openstack.api.nova.server.impl.StopServer;
import com.inforstack.openstack.api.nova.server.impl.SuspendServer;
import com.inforstack.openstack.api.nova.server.impl.UnpauseServer;
import com.inforstack.openstack.controller.model.InstanceModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/instance")
public class InstanceController {

  private static final Logger log = new Logger(InstanceController.class);

  @Autowired
  private ServerService serverService;

  @Autowired
  private KeystoneService keystoneService;

  @Autowired
  private Validator validator;

  private final String INSTANCE_MODULE_HOME = "admin/modules/Instance";

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModule(Model model, HttpServletRequest request) {
    return INSTANCE_MODULE_HOME + "/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrap(Model model) {
    model.addAttribute(Constants.PAGER_PAGE_INDEX, Constants.DEFAULT_PAGE_INDEX);
    model.addAttribute(Constants.PAGER_PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
    return INSTANCE_MODULE_HOME + "/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String template(Model model) {
    return INSTANCE_MODULE_HOME + "/scripts/template";
  }

  @RequestMapping(value = "/getPagerInstanceList", method = RequestMethod.POST)
  public String getPagerInstances(Model model, Integer pageIndex, Integer pageSize) {
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
    List<InstanceModel> imList = new ArrayList<InstanceModel>();
    try {
      Access access = keystoneService.getAdminAccess();
      // Tenant tenant = access.getToken().getTenant();
      if (access != null) {
        log.debug("listing server instances");
        Server[] servers = serverService.listServers(access, true);
        if (servers != null) {
          InstanceModel im = null;
          Flavor flavor = null;
          for (Server server : servers) {
            im = new InstanceModel();
            im.setVmid(server.getId());
            im.setVmname(server.getName());
            im.setStatus(server.getStatus());
            im.setStatusdisplay(OpenstackUtil.getMessage(server.getStatus() + ".status.vm"));
            im.setTenantId(server.getTenant());
            im.setStarttime(server.getCreated());
            im.setUpdatetime(server.getUpdated());
            im.setTaskStatus(server.getTask());
            im.setAssignedto(access.getUser().getUsername());
            im.setAccesspoint("");
            Addresses addresses = server.getAddresses();
            if (addresses.getPrivateList() != null) {
              List<String> privateIps = new ArrayList<String>();
              for (Address address : addresses.getPrivateList()) {
                privateIps.add(address.getAddr());
              }
              if (privateIps.size() > 0) {
                im.setPrivateips(privateIps);
              }
            }

            if (addresses.getPublicList() != null) {
              List<String> publicIps = new ArrayList<String>();
              for (Address address : addresses.getPublicList()) {
                publicIps.add(address.getAddr());
              }
              if (publicIps.size() > 0) {
                im.setPublicips(publicIps);
              }
            }
            flavor = server.getFlavor();
            if (flavor != null) {
              im.setCpus(flavor.getVcpus());
              im.setMaxcpus(flavor.getVcpus());
              im.setMemory(flavor.getRam());
              im.setMaxmemory(flavor.getRam());
              im.setDisksize(flavor.getDisk());
            }
            im.setOstype(server.getImage().getName());
            imList.add(im);
          }
        }

        PagerModel<InstanceModel> page = new PagerModel<InstanceModel>(imList, pageSze);
        imList = page.getPagedData(pageIdx);
        model.addAttribute("pageIndex", pageIdx);
        model.addAttribute("pageSize", pageSze);
        model.addAttribute("pageTotal", page.getTotalRecord());
        model.addAttribute("dataList", imList);
      }
    } catch (OpenstackAPIException e) {
      System.out.println(e.getMessage());
      log.error(e.getMessage(), e);
    }
    return INSTANCE_MODULE_HOME + "/tr";
  }

  @RequestMapping(value = "/imcontrol", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String controlInstance(Model model, String executecommand, String vmid) {
    try {
      Access access = keystoneService.getAdminAccess();
      if (!StringUtil.isNullOrEmpty(executecommand) && !StringUtils.isNullOrEmpty(vmid)) {
        ServerAction action = null;
        Server server = new Server();
        server.setId(vmid);
        if (executecommand.equals("poweroff")) {
          action = new StopServer();
        } else if (executecommand.equals("pause")) {
          action = new PauseServer();
        } else if (executecommand.equals("unpause")) {
          action = new UnpauseServer();
        } else if (executecommand.equals("suspend")) {
          action = new SuspendServer();
        } else if (executecommand.equals("resume")) {
          action = new ResumeServer();
        } else if (executecommand.equals("poweron")) {
          action = new StartServer();
        } else if (executecommand.equals("removevm")) {
          serverService.removeServer(access, server);
        }

        if (action != null) {
          serverService.doServerAction(access, server, action);
        }
      }
    } catch (OpenstackAPIException e) {
      return Constants.JSON_STATUS_EXCEPTION;
    }

    return Constants.JSON_STATUS_DONE;
  }

  @RequestMapping(value = "/createInstance", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  Map<String, Object> createInstance(Model model, InstanceModel vmModel) {
    Server newServer = new Server();
    Map<String, Object> ret = new HashMap<String, Object>();
    String errorMsg = ValidateUtil.validModel(validator, "admin", vmModel);
    if (errorMsg != null) {
      ret.put(Constants.JSON_ERROR_STATUS, errorMsg);
      return ret;
    }
    newServer.setName(vmModel.getVmname());
    newServer.setImageRef(vmModel.getImageId());
    newServer.setFlavorRef(vmModel.getFlavorId());
    try {
      Access access = keystoneService.getAdminAccess();
      serverService.createServer(access, newServer);
      ret.put(Constants.JSON_SUCCESS_STATUS, "success");
    } catch (OpenstackAPIException e) {
      log.error(e.getMessage(), e);
      ret.put(Constants.JSON_ERROR_STATUS, e.getMessage());
      return ret;
    }
    return ret;
  }

  @RequestMapping(value = "/getInstance", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  InstanceModel retrieveInstance(Model model, String vmId) {
    InstanceModel im = new InstanceModel();
    if (!StringUtil.isNullOrEmpty(vmId, false)) {
      try {
        Access access = keystoneService.getAdminAccess();
        if (access != null) {
          Server server = serverService.getServer(access, vmId, false);
          if (server != null) {
            im.setTaskStatus(server.getTask());
            im.setStatus(server.getStatus());
            List<String> privateIps = new ArrayList<String>();
            if (server.getAddresses() != null && server.getAddresses().getPrivateList() != null) {
              Address[] addresses = server.getAddresses().getPrivateList();
              for (Address addr : addresses) {
                privateIps.add(addr.getAddr());
              }
            }
            if (privateIps.size() > 0) {
              im.setPrivateips(privateIps);
            }

            List<String> publicIps = new ArrayList<String>();
            if (server.getAddresses() != null && server.getAddresses().getPublicList() != null) {
              Address[] addresses = server.getAddresses().getPublicList();
              for (Address addr : addresses) {
                publicIps.add(addr.getAddr());
              }
            }
            if (publicIps.size() > 0) {
              im.setPublicips(publicIps);
            }
            im.setStatusdisplay(OpenstackUtil.getMessage(server.getStatus() + ".status.vm"));
          }
        }

      } catch (OpenstackAPIException e) {
        System.out.println(e.getMessage());
        if (e.getMessage().contains("404") || e.getMessage().contains("Can not fetch data")) {
          log.error(e.getMessage());
          im.setStatus("deleted");
          im.setStatusdisplay(OpenstackUtil.getMessage("deleted.status.vm"));
        } else {
          log.error(e.getMessage(), e);
        }

      }
    }
    return im;
  }
}
