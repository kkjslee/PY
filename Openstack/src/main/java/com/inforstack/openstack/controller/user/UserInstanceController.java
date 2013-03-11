package com.inforstack.openstack.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.inforstack.openstack.api.nova.flavor.FlavorService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerAction;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.api.nova.server.impl.PauseServer;
import com.inforstack.openstack.api.nova.server.impl.ResumeServer;
import com.inforstack.openstack.api.nova.server.impl.StartServer;
import com.inforstack.openstack.api.nova.server.impl.StopServer;
import com.inforstack.openstack.api.nova.server.impl.SuspendServer;
import com.inforstack.openstack.api.nova.server.impl.UnpauseServer;
import com.inforstack.openstack.controller.model.AttachmentModel;
import com.inforstack.openstack.controller.model.InstanceModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.item.ItemService;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.order.period.OrderPeriod;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.UserService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/user/instance")
public class UserInstanceController {

	private static final Logger log = new Logger(UserInstanceController.class);

	@Autowired
	private InstanceService instanceService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private KeystoneService keystoneService;
	
	@Autowired
	private FlavorService flavorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;
	
	private final String INSTANCE_MODULE_HOME = "user/modules/Instance";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return INSTANCE_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/scripts/bootstrap", method = RequestMethod.GET)
	public String bootstrap(Model model) {
		model.addAttribute(Constants.PAGER_PAGE_INDEX,
				Constants.DEFAULT_PAGE_INDEX);
		model.addAttribute(Constants.PAGER_PAGE_SIZE,
				Constants.DEFAULT_PAGE_SIZE);
		return INSTANCE_MODULE_HOME + "/scripts/bootstrap";
	}

	@RequestMapping(value = "/scripts/template", method = RequestMethod.GET)
	public String template(Model model) {
		return INSTANCE_MODULE_HOME + "/scripts/template";
	}
	
	@RequestMapping(value = "/modules/instanceStatus", method = RequestMethod.GET)
	public String getInstanceStatus(Model model) {
		return INSTANCE_MODULE_HOME + "/status";
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

		Tenant tenant = SecurityUtils.getTenant();
		
		List<Instance> instanceList = this.instanceService.findInstanceFromTenant(tenant, Constants.INSTANCE_TYPE_VM, null, "deleted");
		
		PagerModel<Instance> page = new PagerModel<Instance>(instanceList, pageSze);
		instanceList = page.getPagedData(pageIdx);
		if (instanceList != null) {
			String username = SecurityUtils.getUserName();
			String password = this.userService.getOpenstackUserPassword();
			try {
				Access access = keystoneService.getAccess(username, password, tenant.getUuid(), true);
				for (Instance instance : instanceList) {
					VirtualMachine vm = this.instanceService.findVirtualMachineFromUUID(instance.getUuid());
					
					InstanceModel im = new InstanceModel();
					
					im.setVmid(vm.getUuid());
					im.setTaskStatus(instance.getTask());
					im.setStatus(instance.getStatus());
					im.setVmname(instance.getName());
					im.setStatusdisplay(OpenstackUtil.getMessage(instance.getStatus() + ".status.vm"));
					im.setTenantId(tenant.getUuid());
					im.setStarttime(instance.getCreateTime());
					im.setUpdatetime(instance.getUpdateTime());
					if(instance.getStatus().equalsIgnoreCase("deleted")){
						im.setDeletedTime(instance.getUpdateTime());
					}
					im.setAssignedto(username);
					im.setAccesspoint("");
					
					try {
						im.setVnc(this.serverService.getVNCLink(access, instance.getUuid(), "novnc"));
					} catch (OpenstackAPIException e) {					
					}
					
					DataCenter dataCenter = this.instanceService.getDataCenterFromInstance(instance);
					im.setRegion(dataCenter.getName().getI18nContent());
					
					OrderPeriod period = this.instanceService.getPeriodFromInstance(instance);
					im.setPeriod(period.getName().getI18nContent());
					
					ItemSpecification flavorItem = this.itemService.getItemSpecificationFromRefId(ItemSpecification.OS_TYPE_FLAVOR_ID, vm.getFlavor());
					if (flavorItem != null) {
						im.setFlavorName(flavorItem.getName().getI18nContent());
					}
					
					Map<String, String> tempAddress = new HashMap<String, String>();
					tempAddress.put("Private", vm.getFixedIp());
					im.setAddresses(tempAddress);
					
					ItemSpecification imageItem = this.itemService.getItemSpecificationFromRefId(ItemSpecification.OS_TYPE_IMAGE_ID, vm.getImage());
					if (imageItem != null) {
						im.setOstype(imageItem.getName().getI18nContent());
					}
					
					imList.add(im);
				}
			} catch (OpenstackAPIException e) {
				
			}
			
			model.addAttribute("pageIndex", pageIdx);
			model.addAttribute("pageSize", pageSze);
			model.addAttribute("pageTotal", page.getTotalRecord());
			model.addAttribute("dataList", imList);
		}
		return INSTANCE_MODULE_HOME + "/tr";
	}

	@RequestMapping(value = "/imcontrol", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String controlInstance(Model model, String executecommand, String vmid, Boolean freeResources) {
		try {
			String username = SecurityUtils.getUserName();
			String password = this.userService.getOpenstackUserPassword();
			Tenant tenant = SecurityUtils.getTenant();
			Access access = keystoneService.getAccess(username, password, tenant.getUuid(), true);

			if (!StringUtil.isNullOrEmpty(executecommand)
					&& !StringUtils.isNullOrEmpty(vmid)) {
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
					//serverService.removeServer(access, server);
					boolean free = (freeResources == null || freeResources.booleanValue());
					this.instanceService.removeVM(SecurityUtils.getUser(), tenant, server.getId(), free);
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
	/***
	 * get instance details
	 * @param model
	 * @param vmId
	 * @return
	 */
	@RequestMapping(value = "/getInstance", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	InstanceModel retrieveInstance(Model model, String vmId) {
		InstanceModel im = new InstanceModel();
		if (!StringUtil.isNullOrEmpty(vmId, false)) {
			String username = SecurityUtils.getUserName();
			String password = this.userService.getOpenstackUserPassword();
			Tenant tenant = SecurityUtils.getTenant();
			try {
				Access access = keystoneService.getAccess(username, password, tenant.getUuid(), true);
				Instance instance = this.instanceService.findInstanceFromUUID(vmId);
				VirtualMachine vm = this.instanceService.findVirtualMachineFromUUID(vmId);
				
				im.setVmid(vm.getUuid());
				im.setTaskStatus(instance.getTask());
				im.setStatus(instance.getStatus());
				im.setVmname(instance.getName());
				im.setStatusdisplay(OpenstackUtil.getMessage(instance.getStatus() + ".status.vm"));
				im.setTenantId(tenant.getUuid());
				im.setStarttime(instance.getCreateTime());
				im.setUpdatetime(instance.getUpdateTime());
				if(instance.getStatus().equalsIgnoreCase("deleted")){
					im.setDeletedTime(instance.getUpdateTime());
				}
				im.setAssignedto(username);
				im.setAccesspoint("");
				
				List<Instance> subInstances = instance.getSubInstance();
				if (subInstances.size() > 0) {
					for (Instance subInstance : subInstances) {
						switch (subInstance.getType()) {
							case Constants.INSTANCE_TYPE_VOLUME: {
								AttachmentModel am = new AttachmentModel();
								am.setId(subInstance.getUuid());
								am.setVolume(subInstance.getName());
								im.setAttachmentModel(am);
								break;
							}
							case Constants.INSTANCE_TYPE_IP: {
								break;
							}
						}
					}
				}
				
				try {
					im.setVnc(this.serverService.getVNCLink(access, instance.getUuid(), "novnc"));
				} catch (OpenstackAPIException e) {					
				}
				
				DataCenter dataCenter = this.instanceService.getDataCenterFromInstance(instance);
				im.setRegion(dataCenter.getName().getI18nContent());
				
				OrderPeriod period = this.instanceService.getPeriodFromInstance(instance);
				im.setPeriod(period.getName().getI18nContent());
				
				ItemSpecification flavorItem = this.itemService.getItemSpecificationFromRefId(ItemSpecification.OS_TYPE_FLAVOR_ID, vm.getFlavor());
				if (flavorItem != null) {
					im.setFlavorName(flavorItem.getName().getI18nContent());
				}
				
				Map<String, String> tempAddress = new HashMap<String, String>();
				tempAddress.put("Private", vm.getFixedIp());
				im.setAddresses(tempAddress);
				
				ItemSpecification imageItem = this.itemService.getItemSpecificationFromRefId(ItemSpecification.OS_TYPE_IMAGE_ID, vm.getImage());
				if (imageItem != null) {
					im.setOstype(imageItem.getName().getI18nContent());
				}
			} catch (OpenstackAPIException e) {
				System.out.println(e.getMessage());
				if (e.getMessage().contains("404")
						|| e.getMessage().contains("Can not fetch data")) {
					log.error(e.getMessage());
					im.setStatus("deleted");
					im.setStatusdisplay(OpenstackUtil
							.getMessage("deleted.status.vm"));
				} else {
					log.error(e.getMessage(), e);
				}

			}
		}
		return im;
	}

	@RequestMapping(value = "/showInstanceDetails", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showInstanceDetails(Model model, String vmId,
			HttpServletRequest request, HttpServletResponse response) {
		InstanceModel instance = retrieveInstance(model, vmId);
		VirtualMachine vm = this.instanceService.findVirtualMachineFromUUID(vmId);
		try {
			ItemSpecification flavorItem = this.itemService.getItemSpecificationFromRefId(ItemSpecification.OS_TYPE_FLAVOR_ID, vm.getFlavor());
			if (flavorItem != null) {
				instance.setFlavorName(flavorItem.getName().getI18nContent());
			}
			
			Flavor flavor = this.flavorService.getFlavor(vm.getFlavor());
			instance.setCpus(flavor.getVcpus());
			instance.setMemory(flavor.getRam());
			instance.setDisksize(flavor.getDisk());
			instance.setFlavorId(flavor.getId());
		} catch (OpenstackAPIException e) {
		}
		
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.vmid", "[hidden]");
		conf.put("form.vmname",
				"[custom]<input type='text' id='vmname' name='vmname' value='"
						+ instance.getVmname()
						+ "'/><a href='#' onclick='updateInstanceName(this)'>"
						+ OpenstackUtil.getMessage("update.button") + "</a>");
		
		conf.put("form.vmname", "[plain]" + instance.getVmname());
		conf.put("form.statusDisplay", "[plain]" + instance.getStatusdisplay());
		conf.put("form.imagename", "[plain]" + instance.getOstype());
		conf.put("form.cpus", "[plain]" + instance.getCpus());
		conf.put("form.memory", "[plain]" + instance.getMemory());
		conf.put("form.disksize", "[plain]" + instance.getDisksize());
		conf.put(
				"form.starttime",
				"[plain]"
						+ StringUtil.formateDateString(instance.getStarttime()));
		conf.put(
				"form.updatetime",
				"[plain]"
						+ StringUtil.formateDateString(instance.getUpdatetime()));
		// TODO: [ricky]get address
		//conf.put("form.addressString", "[plain]" + instance.getAddressString());

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil.getJspPage(
				"/templates/form.jsp?form.configuration=configuration&type=",
				model.asMap(), request, response);

		if (jspString == null) {
			return OpenstackUtil.buildErrorResponse("error message");
		} else {
			return OpenstackUtil.buildSuccessResponse(jspString);
		}
	}

	/**
	 * status: deleted
	 * 
	 * @param model
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getInstancesWidthStatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getInstancesWidthStatus(Model model,
			String includeStatus, String excludeStatus,
			HttpServletRequest request, HttpServletResponse response) {
		Tenant tenant = SecurityUtils.getTenant();
		String include = null;
		if (!StringUtil.isNullOrEmpty(includeStatus, false)) {
			include = includeStatus;
		}
		String exclude = null;
		if (!StringUtil.isNullOrEmpty(excludeStatus, false)) {
			exclude = excludeStatus;
		}
		List<Instance> instanceList;
		try {
			instanceList = this.instanceService.findInstanceFromTenant(tenant, Constants.INSTANCE_TYPE_VM, include, exclude);
			ArrayList<Object> instanceModelList = new ArrayList<Object>();
			for (Instance instance : instanceList) {
				HashMap<String, String> instanceModel = new HashMap<String, String>();
				instanceModel.put("uuid", instance.getUuid());
				instanceModel.put("name", instance.getName());
				instanceModelList.add(instanceModel);
			}
			return JSONUtil.jsonSuccess(instanceModelList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/updateInstanceWithName", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> updateInstanceName(Model model, InstanceModel vmModel,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> ret = new HashMap<String, Object>();
		String errorMsg = ValidateUtil.validModel(validator, "admin", vmModel);
		if (errorMsg != null) {
			ret.put(Constants.JSON_ERROR_STATUS, errorMsg);
			return ret;
		}
		
		Instance instance = this.instanceService.findInstanceFromUUID(vmModel.getVmid());
		if(instance != null){
			Tenant tenant = SecurityUtils.getTenant();
			instanceService.updateVM(SecurityUtils.getUser(), tenant, vmModel.getVmid(), vmModel.getVmname());
			return JSONUtil.jsonSuccess(null, OpenstackUtil.getMessage("operation.success"));
		}else{
			return JSONUtil.jsonError("not found");
		}
		
	}
	
	@RequestMapping(value = "/getPagerInstanceStatusList", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> getPagerInstancesStatus(Model model, Integer pageIndex, Integer pageSize,HttpServletRequest request, HttpServletResponse response ) {
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

		Tenant tenant = SecurityUtils.getTenant();
		
		List<Instance> instanceList = this.instanceService.findInstanceFromTenant(tenant, Constants.INSTANCE_TYPE_VM, null, "deleted");
		
		PagerModel<Instance> page = new PagerModel<Instance>(instanceList, pageSze);
		instanceList = page.getPagedData(pageIdx);
		if (instanceList != null) {
			for (Instance instance : instanceList) {				
				InstanceModel im = new InstanceModel();
				im = retrieveInstance(model, instance.getUuid());
				imList.add(im);
			}
			Map<String, Object> conf = new LinkedHashMap<String, Object>();
			conf.put("grid.name", "[plain]");
			conf.put("grid.id", "[hidden]");
			conf.put("grid.period", "[plain]");
			conf.put("grid.status", "[plain]");
			conf.put("status.value", "{statusDisplay} ");
			conf.put("grid.starttime", "[plain]");
			conf.put("starttime.label", OpenstackUtil.getMessage("createTime.label"));
			conf.put("grid.billTime", "[plain]");
			conf.put("billTime.label", OpenstackUtil.getMessage("instance.billTime.label"));
			conf.put("grid.stopTime", "[plain]");
			conf.put("stopTime.label", OpenstackUtil.getMessage("instance.futurestopTime.label"));
			conf.put("grid.deletedTime", "[plain]");
			conf.put("deletedTime.label", OpenstackUtil.getMessage("instance.futuredeletedTime.label"));
			conf.put("grid.amount", "[plain]");
			conf.put("amount.label", OpenstackUtil.getMessage("instance.amount.label"));
			conf.put(".forPager", true);
			conf.put(".datas", imList);

			model.addAttribute("configuration", conf);

			String jspString = OpenstackUtil.getJspPage("/templates/grid.jsp?grid.configuration=configuration&type=", model.asMap(), request, response);

			if (jspString == null) {
				return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("order.list.loading.failed"));
			} else {
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("recordTotal", page.getTotalRecord());
				result.put("html", jspString);

				return OpenstackUtil.buildSuccessResponse(result);
			}
		}
		return OpenstackUtil.buildErrorResponse("error");
	}

}
