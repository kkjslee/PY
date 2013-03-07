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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.IPModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.instance.IP;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.item.DataCenter;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

@Controller
@RequestMapping(value = "/user/network")
public class UserNetworkController {

	private static final Logger log = new Logger(UserNetworkController.class);

	private final String NETWORK_MODULE_HOME = "user/modules/network";

	@Autowired
	private InstanceService instanceService;
	
	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return NETWORK_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/modules/template", method = RequestMethod.GET)
	public String template(Model model) {
		return NETWORK_MODULE_HOME + "/template";
	}
	
	@RequestMapping(value = "/getPagerIPList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> getPagerIPList(HttpServletRequest request, HttpServletResponse response, Model model, Integer pageIndex, Integer pageSize) {
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

		List<IPModel> ipList = new ArrayList<IPModel>();

		Tenant tenant = SecurityUtils.getTenant();

		List<Instance> instanceList = this.instanceService.findInstanceFromTenant(tenant, Constants.INSTANCE_TYPE_IP, null, null);

		for (Instance instance : instanceList) {
			String uuid = instance.getUuid();
			IP ip = this.instanceService.findIPFromUUID(uuid);
			IPModel ipModel = new IPModel();
			ipModel.setId(ip.getUuid());
			ipModel.setSubOrderId(instance.getSubOrders().get(0).getId());
			ipModel.setCreated(instance.getCreateTime());
			ipModel.setStatus(instance.getStatus());

			DataCenter dataCenter = this.instanceService.getDataCenterFromInstance(instance);
			ipModel.setZone(dataCenter.getName().getI18nContent());

			String vmId = ip.getVm();
			VirtualMachine vm = null;
			if (vmId != null && !vmId.trim().isEmpty()) {
				vm = this.instanceService.findVirtualMachineFromUUID(vmId);
				ipModel.setVm(vm.getUuid());
			}
			ipList.add(ipModel);
		}

		PagerModel<IPModel> page = new PagerModel<IPModel>(ipList, pageSze);
		ipList = page.getPagedData(pageIdx);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.id", "[hidden]");
		conf.put("grid.statusV", "[hidden]");

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

	@RequestMapping(value = "/ipcontrol", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> controlIP(Model model, String executecommand, String ipId, String serverId) {
		if (StringUtil.isNullOrEmpty(serverId)) {
			return JSONUtil.jsonError("server is null");
		}
		try {
			if (!StringUtil.isNullOrEmpty(executecommand) && !StringUtils.isNullOrEmpty(ipId)) {
				User user = SecurityUtils.getUser();
				Tenant tenant = SecurityUtils.getTenant();
				if (executecommand.equals("associate")) {
					this.instanceService.associateIP(user, tenant, ipId, serverId);
				} else if (executecommand.equals("disassociate")) {
					this.instanceService.disassociateIP(user, tenant, ipId, serverId);
				} else if (executecommand.equals("remove")) {
					this.instanceService.removeIP(user, tenant, ipId);
				}
			}
		} catch (RuntimeException e) {
			return JSONUtil.jsonError(e.getMessage());
		}

		return JSONUtil.jsonSuccess(null, "success");
	}

	/**
	 * mostly for status
	 * 
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/getIPDetail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> getIP(Model model, String uuid) {
		Instance ipInstance = instanceService.findInstanceFromUUID(uuid);
		if (ipInstance != null) {
			IP ip = this.instanceService.findIPFromUUID(uuid);
			IPModel ipModel = new IPModel();
			ipModel.setId(ip.getUuid());
			ipModel.setSubOrderId(ipInstance.getSubOrders().get(0).getId());
			ipModel.setCreated(ipInstance.getCreateTime());
			ipModel.setStatus(ipInstance.getStatus());

			DataCenter dataCenter = this.instanceService.getDataCenterFromInstance(ipInstance);
			ipModel.setZone(dataCenter.getName().getI18nContent());

			String vmId = ip.getVm();
			VirtualMachine vm = null;
			if (vmId != null && !vmId.trim().isEmpty()) {
				vm = this.instanceService.findVirtualMachineFromUUID(vmId);
				ipModel.setVm(vm.getUuid());
			}
			ipModel.setStatus(ipInstance.getStatus());
			return JSONUtil.jsonSuccess(ipModel);
		} else {
			return JSONUtil.jsonError("not found");
		}

	}
	
}
