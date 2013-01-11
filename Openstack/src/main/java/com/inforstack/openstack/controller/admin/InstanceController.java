package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.flavor.Flavor;
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.image.Image;
import com.inforstack.openstack.api.nova.image.ImageService;
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
import com.inforstack.openstack.controller.model.FlavorModel;
import com.inforstack.openstack.controller.model.ImgModel;
import com.inforstack.openstack.controller.model.VMModel;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/instance")
public class InstanceController {

  @Autowired
  private ServerService serverService;

  @Autowired
  private KeystoneService keystoneService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private FlavorService flavorService;

  @RequestMapping(value = "/modules/index", method = RequestMethod.GET)
  public String redirectModule(Model model, HttpServletRequest request) {
    return "admin/modules/Instance/index";
  }

  @RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
  public String bootstrap(Model model) {
    return "admin/modules/Instance/scripts/bootstrap";
  }

  @RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
  public String template(Model model) {
    // retrive images and flavors
    List<ImgModel> imgModels = listImgs(model);
    model.addAttribute("imgModels", imgModels);
    List<FlavorModel> flavorModels = listFlavors(model);
    model.addAttribute("flavorModels", flavorModels);
    return "admin/modules/Instance/scripts/template";
  }

  @RequestMapping(value = "/vmlist", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<VMModel> listVMs(Model model, String loginUser) {
    List<VMModel> vmList = new ArrayList<VMModel>();
    // String username = SecurityUtils.getUserName();
    // String password = SecurityUtils.getUser().getPassword();
    try {
      Access access = keystoneService.getAdminAccess();
      // Tenant tenant = access.getToken().getTenant();
      if (access != null) {
        Server[] servers = serverService.listServers(access, true);
        VMModel vm = null;
        Flavor flavor = null;
        for (Server server : servers) {
          vm = new VMModel();
          vm.setVmid(server.getId());
          vm.setVmname(server.getName());
          vm.setStatus(Constants.vmStatusMap.get(server.getStatus()));
          vm.setStatusdisplay(server.getStatus());
          vm.setTenantId(server.getTenant());
          vm.setStarttime(server.getCreated());
          vm.setUpdatetime(server.getUpdated());
          vm.setAssignedto(access.getUser().getUsername());
          vm.setAccesspoint("");
          Addresses addresses = server.getAddresses();
          if (addresses.getPrivateList() != null) {
            List<String> privateIps = new ArrayList<String>();
            for (Address address : addresses.getPrivateList()) {
              privateIps.add(address.getAddr());
            }
            if (privateIps.size() > 0) {
              vm.setPrivateips(privateIps);
            }
          }

          if (addresses.getPublicList() != null) {
            List<String> publicIps = new ArrayList<String>();
            for (Address address : addresses.getPublicList()) {
              publicIps.add(address.getAddr());
            }
            if (publicIps.size() > 0) {
              vm.setPublicips(publicIps);
            }
          }
          flavor = server.getFlavor();
          vm.setCpus(flavor.getVcpus());
          vm.setMaxcpu(flavor.getVcpus());
          vm.setMemory(flavor.getRam());
          vm.setMaxmemory(flavor.getRam());
          vm.setDisksize(flavor.getDisk());
          vm.setOstype(server.getImage().getName());
          vmList.add(vm);
        }
      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }

    return vmList;
  }

  @RequestMapping(value = "/vmcontrol", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String controlVM(Model model, String executecommand, String vmid) {
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

  @RequestMapping(value = "/createVM", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  String createVM(Model model, String vmName, String imgId, String flavorId) {

    Server newServer = new Server();
    if (StringUtil.isNullOrEmpty(vmName) || StringUtil.isNullOrEmpty(imgId)
        || StringUtil.isNullOrEmpty(flavorId)) {
      return Constants.JSON_STATUS_FAILED;
    }
    newServer.setName(vmName);
    newServer.setImageRef(imgId);
    newServer.setFlavorRef(flavorId);
    try {
      Access access = keystoneService.getAdminAccess();
      serverService.createServer(access, newServer);
    } catch (OpenstackAPIException e) {
      return Constants.JSON_STATUS_EXCEPTION;
    }
    return Constants.JSON_STATUS_DONE;
  }

  @RequestMapping(value = "/imglist", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<ImgModel> listImgs(Model model) {
    List<ImgModel> imgModels = new ArrayList<ImgModel>();
    try {
      Image[] imgs = imageService.listImages();
      ImgModel imgModel = null;
      for (Image img : imgs) {
        if (ValidateUtil.checkValidImg(img)) {
          imgModel = new ImgModel();
          imgModel.setImgId(img.getId());
          imgModel.setImgName(img.getName());
          imgModels.add(imgModel);
        }

      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }

    return imgModels;
  }

  @RequestMapping(value = "/flavorlist", method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody
  List<FlavorModel> listFlavors(Model model) {
    List<FlavorModel> flavorModels = new ArrayList<FlavorModel>();
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
          flavorModels.add(flavorModel);
        }

      }
    } catch (OpenstackAPIException e) {
      e.printStackTrace();
    }

    return flavorModels;
  }

}
