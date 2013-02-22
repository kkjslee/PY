package com.inforstack.openstack.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.api.quantum.Subnet;
import com.inforstack.openstack.controller.model.NetworkModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value = "/admin/quantum")
public class QuantumController {

	private static final Logger log = new Logger(QuantumController.class);

	@Autowired
	private QuantumService quantumService;

	@Autowired
	private KeystoneService keystoneService;

	private final String NETWORK_MODULE_HOME = "admin/modules/Network";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return NETWORK_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/getPagerNetworkList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerNetworks(Integer pageIndex, Integer pageSize,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
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
		List<NetworkModel> nList = new ArrayList<NetworkModel>();
		Access access = null;
		try {
			access = keystoneService.getAdminAccess();
			// Tenant tenant = access.getToken().getTenant();
			if (access != null) {
				Network[] networks = quantumService.listNetworks(access);
				log.debug("listing networks");

				NetworkModel networkModel = null;
				for (Network n : networks) {
					networkModel = new NetworkModel();
					networkModel.setAdminStateUp(n.getAdminStateUp());
					networkModel.setExternal(n.getExternal());
					networkModel.setId(n.getId());
					networkModel.setName(n.getName());
					networkModel.setShared(n.getShared());
					networkModel.setStatus(n.getStatus());
					networkModel.setSubnets(n.getSubnets());
					networkModel.setTenant(n.getTenant());
					nList.add(networkModel);
				}
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}

		PagerModel<NetworkModel> page = new PagerModel<NetworkModel>(nList,
				pageSze);
		nList = page.getPagedData(pageIdx);
		for (NetworkModel w : nList) {
			String[] subnets = w.getSubnets();
			if (subnets != null && subnets.length > 0) {
				String[] subnetNames = new String[subnets.length];
				for (int i = 0; i < subnets.length; i++) {
					Subnet subnet;
					try {
						subnet = quantumService.getSubnet(access, subnets[i]);
						if (subnet != null) {
							subnetNames[i] = subnet.getName();
						} else {
							log.debug("not find subnet with id:" + subnets[i]);
						}
					} catch (OpenstackAPIException e) {
						log.error(e.getMessage(), e);
					}

				}
				w.setSubnetNames(subnetNames);
			}

		}
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.subnets", "[plain]");
		conf.put("subnets.value", "{subnetName}");
		conf.put("grid.shared", "[plain]");
		conf.put("shared.value", "{shareDisplay}");
		conf.put(".datas", nList);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil.getJspPage(
				"/templates/grid.jsp?grid.configuration=configuration&type=",
				model.asMap(), request, response);

		if (jspString == null) {
			return OpenstackUtil.buildErrorResponse(OpenstackUtil
					.getMessage("order.list.loading.failed"));
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("recordTotal", page.getTotalRecord());
			result.put("html", jspString);

			return OpenstackUtil.buildSuccessResponse(result);
		}
	}
}
