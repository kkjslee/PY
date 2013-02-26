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
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.keystone.Access;
import com.inforstack.openstack.api.keystone.KeystoneService;
import com.inforstack.openstack.api.nova.server.Server;
import com.inforstack.openstack.api.nova.server.ServerService;
import com.inforstack.openstack.api.quantum.Network;
import com.inforstack.openstack.api.quantum.Port;
import com.inforstack.openstack.api.quantum.QuantumService;
import com.inforstack.openstack.api.quantum.Subnet;
import com.inforstack.openstack.controller.model.NetworkModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.controller.model.PortModel;
import com.inforstack.openstack.controller.model.SubnetModel;
import com.inforstack.openstack.i18n.dict.Dictionary;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.JSONUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;
import com.inforstack.openstack.utils.ValidateUtil;

@Controller
@RequestMapping(value = "/admin/quantum")
public class QuantumController {

	private static final Logger log = new Logger(QuantumController.class);

	@Autowired
	private QuantumService quantumService;

	@Autowired
	private ServerService serverService;

	@Autowired
	private KeystoneService keystoneService;

	@Autowired
	private Validator validator;

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
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
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
				String[] subnetNamesWithNetwork = new String[subnets.length];
				for (int i = 0; i < subnets.length; i++) {
					Subnet subnet;
					try {
						subnet = quantumService.getSubnet(access, subnets[i]);
						if (subnet != null) {

							String name = subnet.getName();
							if (subnet.getNetwork() != null) {
								// TODO WITH SUBNET NAME
								// name = name + "  " + subnet.getNetwork();
							}
							subnetNamesWithNetwork[i] = name;
						} else {
							log.debug("not find subnet with id:" + subnets[i]);
						}
					} catch (OpenstackAPIException e) {
						log.error(e.getMessage(), e);
					}

				}
				w.setSubnetNamesWithNetwork(subnetNamesWithNetwork);
			}

		}
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("name.value",
				"<a href='#' onclick='showNetWorkDetails(\"{id}\")'>{name}</a>");
		conf.put("grid.subnets", "[plain]");
		conf.put("subnets.value", "{subnetNameString} ");
		conf.put("grid.adminStateUp", "[plain]");
		conf.put("adminStateUp.value", "{adminStateUpDisplay} ");
		conf.put("grid.status", "[plain]");
		conf.put("status.value", "{status} ");
		conf.put("grid.shared", "[plain]");
		conf.put("shared.value", "{shareDisplay} ");
		conf.put("grid.external", "[plain]");
		conf.put("external.value", "{externalDisplay} ");
		conf.put("grid.operation", "[button]edit,remove");
		conf.put("edit.onclick", "showEditNetwork('{id}')");
		conf.put("remove.onclick", "showRemoveNetwork('{id}')");
		conf.put(".datas", nList);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil
				.getJspPage(
						"/templates/pagerGrid.jsp?grid.configuration=configuration&type=",
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

	@RequestMapping(value = "/showCreateNetworkForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showCreateNetworkForm(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]");
		conf.put("form.adminStateUp", "[checkbox]true");
		conf.put("form.shared", "[checkbox]");
		conf.put("form.external", "[checkbox]");

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

	@RequestMapping(value = "/createNetwork", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> createNetwork(Model model, NetworkModel networkModel,
			HttpServletRequest request) {

		String errorMsg = ValidateUtil.validModel(validator, "admin",
				networkModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		Access access = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();
			Network tmpNetwork = null;
			try {
				access = keystoneService.getAdminAccess();
				if (access != null) {
					Network[] networks = quantumService.listNetworks(access);
					log.debug("listing networks");

					for (Network n : networks) {
						if (networkModel.getName().equals(n.getName())) {
							tmpNetwork = n;
							break;
						}
					}
				}
			} catch (OpenstackAPIException e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage(), e);
			}
			if (tmpNetwork != null) {
				return JSONUtil.jsonError(OpenstackUtil.getMessage(
						"name.exists", tmpNetwork.getName()));
			}
			network = this.quantumService.createNetwork(access,
					networkModel.getName(), networkModel.getAdminStateUp(),
					networkModel.getShared(), networkModel.getExternal());
			if (network != null) {
				return JSONUtil.jsonSuccess(network,
						OpenstackUtil.getMessage("network.created.success"));
			}
		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(network,
					OpenstackUtil.getMessage("network.created.failed"));
		}

		return JSONUtil.jsonError(network,
				OpenstackUtil.getMessage("network.created.failed"));
	}

	@RequestMapping(value = "/showEditNetworkForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showEditNetworkForm(Model model,
			NetworkModel networkModel, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(networkModel.getId(), true)) {
			return JSONUtil.jsonError("not found");
		}
		String errorMsg = ValidateUtil.validModel(validator, "admin",
				networkModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		Access access = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();
			if (access != null) {
				Network[] networks = quantumService.listNetworks(access);
				log.debug("listing networks");

				for (Network n : networks) {
					if (networkModel.getId().equals(n.getId())) {
						network = n;
						break;
					}
				}
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}
		if (network == null) {
			return JSONUtil.jsonError("not found");
		}
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]" + network.getName());
		conf.put("form.adminStateUp", "[checkbox]" + network.getAdminStateUp());
		conf.put("form.shared", "[checkbox]" + network.getShared());
		conf.put("form.external", "[checkbox]" + network.getExternal());

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

	@RequestMapping(value = "/editNetwork", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> editNetwork(Model model, NetworkModel networkModel,
			HttpServletRequest request) {
		if (StringUtil.isNullOrEmpty(networkModel.getId(), true)) {
			return JSONUtil.jsonError("not found");
		}
		String errorMsg = ValidateUtil.validModel(validator, "admin",
				networkModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		Access access = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();
			try {
				access = keystoneService.getAdminAccess();
				if (access != null) {
					Network[] networks = quantumService.listNetworks(access);
					log.debug("listing networks");

					for (Network n : networks) {
						if (networkModel.getId().equals(n.getId())) {
							network = n;
							break;
						}
					}
				}
			} catch (OpenstackAPIException e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage(), e);
			}
			if (network == null) {
				return JSONUtil.jsonError("not found");
			}
			this.quantumService.updateNetwork(access, network,
					networkModel.getName(), networkModel.getAdminStateUp(),
					networkModel.getShared(), networkModel.getExternal());
			return JSONUtil.jsonSuccess(network,
					OpenstackUtil.getMessage("update.success"));
		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(network,
					OpenstackUtil.getMessage("update.failed"));
		}
	}

	@RequestMapping(value = "/removeNetwork", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> removeNetwork(Model model, NetworkModel networkModel,
			HttpServletRequest request) {
		if (StringUtil.isNullOrEmpty(networkModel.getId(), true)) {
			return JSONUtil.jsonError("not found");
		}
		String errorMsg = ValidateUtil.validModel(validator, "admin",
				networkModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		Access access = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();
			boolean has = false;
			try {
				// Tenant tenant = access.getToken().getTenant();
				if (access != null) {
					Network[] networks = quantumService.listNetworks(access);
					log.debug("listing networks");

					for (Network n : networks) {
						if (networkModel.getId().equals(n.getId())) {
							has = true;
							break;
						}
					}
				}
			} catch (OpenstackAPIException e) {
				System.out.println(e.getMessage());
				log.error(e.getMessage(), e);
			}
			if (has) {
				this.quantumService.removeNetwork(access, networkModel.getId());
				return JSONUtil.jsonSuccess(network,
						OpenstackUtil.getMessage("remove.success"));
			} else {
				return JSONUtil.jsonError(network,
						OpenstackUtil.getMessage("remove.failed"));
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(network,
					OpenstackUtil.getMessage("remove.failed"));
		}

	}

	@RequestMapping(value = "/getNetworkDetails")
	public String getNetworkDetails(String networkId, Model model,
			HttpServletRequest request) {
		if (StringUtil.isNullOrEmpty(networkId, true)) {
			return "not found under network id:" + networkId;
		}
		Network networkTemp = null;
		NetworkModel netModel = null;
		Access access = null;
		try {
			access = keystoneService.getAdminAccess();
			// Tenant tenant = access.getToken().getTenant();
			if (access != null) {
				Network[] networks = quantumService.listNetworks(access);
				log.debug("listing networks");

				for (Network n : networks) {
					if (n.getId().equals(networkId)) {
						networkTemp = n;
					}

				}
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}

		if (networkTemp != null) {
			netModel = new NetworkModel();
			netModel.setId(networkTemp.getId());
			netModel.setName(networkTemp.getName());
			netModel.setShared(networkTemp.getShared());
			netModel.setStatus(networkTemp.getStatus());
			netModel.setTenant(networkTemp.getTenant());
			netModel.setExternal(networkTemp.getExternal());
			netModel.setSubnets(networkTemp.getSubnets());
			netModel.setAdminStateUp(networkTemp.getAdminStateUp());
			model.addAttribute("network", netModel);
		}
		model.addAttribute("networkId", networkId);
		return NETWORK_MODULE_HOME + "/details";

	}

	@RequestMapping(value = "/getPagerSubnetList", produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerSubnets(String networkId, Integer pageIndex,
			Integer pageSize, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(networkId, true)) {
			return JSONUtil.jsonError("not found");
		}
		int pageIdx = -1;
		int pageSze = 0;
		if (pageIndex == null || pageIndex == 0) {
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
			pageSze = Constants.DEFAULT_PAGE_SIZE;
		} else {
			pageSze = pageSize;
		}
		Access access = null;
		Network networkTemp = null;
		try {
			access = keystoneService.getAdminAccess();
			// Tenant tenant = access.getToken().getTenant();
			if (access != null) {
				Network[] networks = quantumService.listNetworks(access);
				log.debug("listing networks");

				for (Network n : networks) {
					if (n.getId().equals(networkId)) {
						networkTemp = n;
					}

				}
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}

		List<Subnet> sList = new ArrayList<Subnet>();
		if (networkTemp != null) {
			String[] subnets = networkTemp.getSubnets();
			if (subnets != null && subnets.length > 0) {
				for (int i = 0; i < subnets.length; i++) {
					Subnet subnet = null;
					try {
						subnet = quantumService.getSubnet(access, subnets[i]);
						if (subnet != null) {
							sList.add(subnet);
						} else {
							log.debug("not find subnet with id:" + subnets[i]);
						}
					} catch (OpenstackAPIException e) {
						log.error(e.getMessage(), e);
					}

				}
			}

		}
		PagerModel<Subnet> page = new PagerModel<Subnet>(sList, pageSze);
		sList = page.getPagedData(pageIdx);
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.cidr", "[plain]");
		conf.put("grid.ipVersion", "[plain]");
		conf.put("ipVersion.value", "IPv" + "{ipVersion} ");
		conf.put("grid.gateway", "[plain]");
		conf.put("grid.operation", "[button]edit,remove");
		conf.put("edit.onclick", "showEditSubnet('{id}')");
		conf.put("remove.onclick", "showRemoveSubnet('{id}')");
		conf.put(".datas", sList);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil
				.getJspPage(
						"/templates/pagerGrid.jsp?grid.configuration=configuration&type=",
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

	@RequestMapping(value = "/getPagerPortList", produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerPorts(String networkId, Integer pageIndex,
			Integer pageSize, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtil.isNullOrEmpty(networkId, true)) {
			return JSONUtil.jsonError("not found");
		}
		int pageIdx = -1;
		int pageSze = 0;
		if (pageIndex == null || pageIndex == 0) {
			pageIdx = Constants.DEFAULT_PAGE_INDEX;
		} else {
			pageIdx = pageIndex;
		}
		if (pageSize == null) {
			pageSze = Constants.DEFAULT_PAGE_SIZE;
		} else {
			pageSze = pageSize;
		}
		Access access = null;
		try {
			access = keystoneService.getAdminAccess();
		} catch (OpenstackAPIException e1) {
			log.error(e1.getMessage(), e1);
		}
		Port[] pAllList = null;
		List<PortModel> pList = new ArrayList<PortModel>();
		PortModel pModel = null;
		try {
			pAllList = quantumService.listPorts(access);
			if (pAllList != null && pAllList.length > 0) {
				for (Port p : pAllList) {
					pModel = new PortModel();
					pModel.setAdminStateUp(p.getAdminStateUp());
					pModel.setDeviceId(p.getDevice());
					Server server = serverService.getServer(access,
							p.getDevice(), false);
					if (server != null) {
						pModel.setDeviceName(server.getName());
					} else {
						pModel.setDeviceName("");
					}
					pModel.setId(p.getId());
					pModel.setIps(p.getIps());
					pModel.setMac(p.getMac());
					pModel.setName(p.getName());
					pModel.setNetwork(p.getNetwork());
					pModel.setStatus(p.getStatus());
					pModel.setTenant(p.getTenant());
					pList.add(pModel);

				}
			}
		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
		}
		PagerModel<PortModel> page = new PagerModel<PortModel>(pList, pageSze);
		pList = page.getPagedData(pageIdx);
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.ips", "[plain]");
		conf.put("ips.value", "{ipsDisplay} ");
		conf.put("grid.device", "[plain]");
		conf.put("device.value", "{deviceName} ");
		conf.put("grid.status", "[plain]");
		conf.put("grid.adminStateUp", "[plain]");
		conf.put("grid.operation", "[button]remove");
		conf.put("remove.onclick", "showRemovePort('{id}')");
		/*
		 * conf.put("grid.operation", "[button]edit,remove");
		 * conf.put("edit.onclick", "showEditPort('{id}')");
		 * conf.put("remove.onclick", "showRemovePort('{id}')");
		 */
		conf.put(".datas", pList);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil
				.getJspPage(
						"/templates/pagerGrid.jsp?grid.configuration=configuration&type=",
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

	@RequestMapping(value = "/showCreatePortForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showCreatePortForm(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]");
		conf.put("form.adminStateUp", "[checkbox]");
		conf.put("form.deviceId", "[text]");
		conf.put("form.deviceOwner", "[text]");

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

	@RequestMapping(value = "/showEditPortForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showEditPortForm(Model model, String portId,
			HttpServletRequest request, HttpServletResponse response) {

		if (StringUtil.isNullOrEmpty(portId, true)) {
			return JSONUtil.jsonError("not found subnet");
		}
		Port port = null;
		Access access = null;
		PortModel pModel = null;
		try {
			access = keystoneService.getAdminAccess();
			Port[] portList = quantumService.listPorts(access);
			for (int i = 0; i < portList.length; i++) {
				if (portList[i].getId().equals(portId)) {
					port = portList[i];
					break;
				}
			}
		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(portId,
					OpenstackUtil.getMessage("update.failed"));
		}
		if (port == null) {
			log.debug("not found port id:" + portId);
			return JSONUtil.jsonError("not found port id: " + portId);
		} else {
			pModel = new PortModel();
			pModel.setAdminStateUp(port.getAdminStateUp());
			pModel.setDeviceId(port.getDevice());
			// TODO OWNER
		}
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]" + pModel.getName());
		conf.put("form.adminStateUp", "[checkbox]" + pModel.getAdminStateUp());
		conf.put("form.deviceId", "[text]" + pModel.getDeviceId());
		// TODO owner
		conf.put("form.deviceOwner", "[text]");

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

	@RequestMapping(value = "/createPort", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> createPort(Model model, PortModel portModel,
			HttpServletRequest request) {

		String errorMsg = ValidateUtil
				.validModel(validator, "admin", portModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		if (StringUtil.isNullOrEmpty(portModel.getNetwork())) {
			log.error("dnetwork is null");
			return JSONUtil.jsonError("network not found");
		}
		Access access = null;
		Port port = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();
			Network[] networks = quantumService.listNetworks(access);
			log.debug("listing networks");

			for (Network n : networks) {
				if (n.getId().equals(portModel.getNetwork())) {
					network = n;
				}
			}
			if (network != null) {
				Server server = null;
				if (!StringUtil.isNullOrEmpty(portModel.getDeviceId(), true)) {
					server = serverService.getServer(access,
							portModel.getDeviceId(), false);
					if (server == null) {
						log.error("not found server:" + portModel.getDeviceId());
						return JSONUtil.jsonError(OpenstackUtil.getMessage(
								"device.notfound", portModel.getDeviceId()));
					}

				}
				port = new Port();
				port.setAdminStateUp(portModel.getAdminStateUp());
				port.setDevice(portModel.getDeviceId());

				port.setName(portModel.getName());
				port.setNetwork(port.getNetwork());
				// TODO OWNER
				port = this.quantumService.createPort(access, port, server);
				if (port.getId() != null) {
					return JSONUtil.jsonSuccess(port,
							OpenstackUtil.getMessage("created.success"));
				}

			} else {
				log.error("not find network:" + portModel.getNetwork());
				return JSONUtil.jsonError(OpenstackUtil.getMessage(
						"network.notfound", portModel.getNetwork()));
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(portModel,
					OpenstackUtil.getMessage("created.failed"));
		}

		return JSONUtil.jsonError(portModel,
				OpenstackUtil.getMessage("created.failed"));
	}

	// TODO
	@RequestMapping(value = "/editPort", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> editPort(Model model, PortModel portModel,
			HttpServletRequest request) {

		String errorMsg = ValidateUtil
				.validModel(validator, "admin", portModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		String pId = portModel.getId();
		if (StringUtil.isNullOrEmpty(pId, true)) {
			return JSONUtil.jsonError("not found subnet");
		}

		Access access = null;
		Port port = null;
		try {
			access = keystoneService.getAdminAccess();
			Server server = null;
			if (!StringUtil.isNullOrEmpty(portModel.getDeviceId(), true)) {
				server = serverService.getServer(access,
						portModel.getDeviceId(), false);
				if (server == null) {
					log.error("not found server:" + portModel.getDeviceId());
					return JSONUtil.jsonError(OpenstackUtil.getMessage(
							"device.notfound", portModel.getDeviceId()));
				}

			}
			Port[] portList = quantumService.listPorts(access);
			for (int i = 0; i < portList.length; i++) {
				if (portList[i].getId().equals(pId)) {
					port = portList[i];
					port.setAdminStateUp(portModel.getAdminStateUp());
					port.setDevice(portModel.getDeviceId());
					port.setName(portModel.getName());
					// TODO owner
					break;
				}
			}
			if (port != null) {
				// TODO UPDATE
				// this.quantumService(access, port);
				return JSONUtil.jsonSuccess(port,
						OpenstackUtil.getMessage("update.success"));
			} else {
				log.error("not find port id:" + portModel.getId());
				return JSONUtil.jsonError("not find port id:"
						+ portModel.getId());
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(portModel,
					OpenstackUtil.getMessage("update.failed"));
		}

	}

	@RequestMapping(value = "/removePort", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> removePort(Model model, String portId,
			HttpServletRequest request) {

		if (StringUtil.isNullOrEmpty(portId, true)) {
			log.debug("not found port:" + portId);
			return JSONUtil.jsonError("not found port from request");
		}

		Access access = null;
		Port port = null;
		try {
			access = keystoneService.getAdminAccess();
			Port[] portList = quantumService.listPorts(access);
			for (int i = 0; i < portList.length; i++) {
				if (portList[i].getId().equals(portId)) {
					port = portList[i];
					break;
				}
			}
			if (port != null) {
				this.quantumService.removeSubnet(access, portId);
				return JSONUtil.jsonSuccess(port,
						OpenstackUtil.getMessage("update.success"));
			} else {
				log.debug("not found port :" + portId);
				return JSONUtil.jsonError("not found port :" + portId);
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(port,
					OpenstackUtil.getMessage("update.failed"));
		}

	}

	@RequestMapping(value = "/showCreateSubnetForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showCreateSubnetForm(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		List<Dictionary> ipDic = new ArrayList<Dictionary>();
		Dictionary dic = new Dictionary();
		dic.setKey("4");
		dic.setValue("IPv4");
		ipDic.add(dic);

		dic = new Dictionary();
		dic.setKey("6");
		dic.setValue("IPv6");
		ipDic.add(dic);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]");
		conf.put("form.cidr", "[text]");
		conf.put("form.ipVersion", "[select]");
		conf.put("ipVersion.options", ipDic);
		conf.put("form.gateway", "[text]");
		conf.put("form.disableGateway", "[checkbox]");
		conf.put("form.enableDHCP", "[checkbox]");
		conf.put("form.poolString", "[textarea]");
		conf.put("form.dnsNamesString", "[textarea]");
		conf.put("form.hostRoutesString", "[textarea]");

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

	@RequestMapping(value = "/showEditSubnetForm", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> showEditSubnetForm(Model model, String subnetId,
			HttpServletRequest request, HttpServletResponse response) {

		String sId = subnetId;
		if (StringUtil.isNullOrEmpty(sId, true)) {
			return JSONUtil.jsonError("not found subnet");
		}
		Subnet subnet = null;
		Access access = null;
		SubnetModel sModel = null;
		try {
			access = keystoneService.getAdminAccess();
			Subnet[] subnetList = quantumService.listSubnets(access);
			for (int i = 0; i < subnetList.length; i++) {
				if (subnetList[i].getId().equals(sId)) {
					subnet = subnetList[i];
					break;
				}
			}
		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(subnet,
					OpenstackUtil.getMessage("update.failed"));
		}
		if (subnet == null) {
			log.debug("not find subnet id:" + subnetId);
			return JSONUtil.jsonError("not found subnet id:" + subnetId);
		} else {
			sModel = new SubnetModel();
			sModel.setCidr(subnet.getCidr());
			// TODO GATEWAY
			sModel.setEnableDHCP(subnet.getDhcp());
			sModel.setGateway(subnet.getGateway());
			sModel.setIpVersion(subnet.getIpVersion());
			sModel.setName(subnet.getName());
			sModel.setNetwork(subnet.getNetwork());
			sModel.setPools(subnet.getPools());
			sModel.setTenant(subnet.getTenant());

		}
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.name", "[text]" + sModel.getName());
		conf.put("form.cidr", "[plain]" + sModel.getCidr());
		conf.put("form.gateway", "[text]" + sModel.getGateway());
		conf.put("form.disableGateway",
				"[checkbox]" + sModel.getDisableGateway());
		conf.put("form.enableDHCP", "[checkbox]" + sModel.getEnableDHCP());
		conf.put("form.dnsNamesString",
				"[textarea]" + sModel.getDnsNamesString());
		conf.put("form.hostRoutesString",
				"[textarea]" + sModel.getHostRoutesString());

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

	@RequestMapping(value = "/createSubnet", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> createSubnet(Model model, SubnetModel subnetModel,
			HttpServletRequest request) {

		String errorMsg = ValidateUtil.validModel(validator, "admin",
				subnetModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		if (StringUtil.isNullOrEmpty(subnetModel.getNetwork(), true)) {
			log.debug("not find network");
			return JSONUtil.jsonError("not find network");
		}
		Access access = null;
		Subnet subnet = null;
		Network network = null;
		try {
			access = keystoneService.getAdminAccess();

			if (access != null) {
				Network[] networks = quantumService.listNetworks(access);
				log.debug("listing networks");

				for (Network n : networks) {
					if (n.getId().equals(subnetModel.getNetwork())) {
						network = n;
					}
				}
			}

			if (network != null) {
				subnet = this.quantumService.createSubnet(access,
						subnetModel.getNetwork(), subnetModel.getName(),
						subnetModel.getIpVersion(), subnetModel.getCidr(),
						subnetModel.getPoolsFormat());
				if (subnet != null) {
					return JSONUtil.jsonSuccess(subnet,
							OpenstackUtil.getMessage("subnet.created.success"));
				}
			} else {
				log.error("not find network:" + subnetModel.getNetwork());
				return JSONUtil.jsonError("not find network:"
						+ subnetModel.getNetwork());
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(subnet,
					OpenstackUtil.getMessage("subnet.created.failed"));
		}

		return JSONUtil.jsonError(subnet,
				OpenstackUtil.getMessage("subnet.created.failed"));
	}

	@RequestMapping(value = "/editSubnet", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> editSubnet(Model model, SubnetModel subnetModel,
			HttpServletRequest request) {

		String errorMsg = ValidateUtil.validModel(validator, "admin",
				subnetModel);
		if (errorMsg != null) {
			return JSONUtil.jsonError(errorMsg);
		}
		String sId = subnetModel.getId();
		if (StringUtil.isNullOrEmpty(sId, true)) {
			return JSONUtil.jsonError("not found subnet");
		}

		Access access = null;
		Subnet subnet = null;
		try {
			access = keystoneService.getAdminAccess();
			Subnet[] subnetList = quantumService.listSubnets(access);
			for (int i = 0; i < subnetList.length; i++) {
				if (subnetList[i].getId().equals(sId)) {
					subnet = subnetList[i];
					subnet.setDhcp(subnetModel.getEnableDHCP());
					subnet.setGateway(subnetModel.getGateway());
					subnet.setName(subnetModel.getName());
					// TODO dns host routes
					break;
				}
			}
			if (subnet != null) {
				this.quantumService.updateSubnet(access, subnet);
				return JSONUtil.jsonSuccess(subnet,
						OpenstackUtil.getMessage("update.success"));
			} else {
				log.debug("not find subnet id:" + subnetModel.getId());
				return JSONUtil.jsonError("not find subnet"
						+ subnetModel.getId());
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(subnet,
					OpenstackUtil.getMessage("update.failed"));
		}

	}

	@RequestMapping(value = "/removeSubnet", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> removeSubnet(Model model, String subnetId,
			HttpServletRequest request) {

		if (StringUtil.isNullOrEmpty(subnetId, true)) {
			return JSONUtil.jsonError("not found subnet");
		}

		Access access = null;
		Subnet subnet = null;
		try {
			access = keystoneService.getAdminAccess();
			Subnet[] subnetList = quantumService.listSubnets(access);
			for (int i = 0; i < subnetList.length; i++) {
				if (subnetList[i].getId().equals(subnetId)) {
					subnet = subnetList[i];
					break;
				}
			}
			if (subnet != null) {
				this.quantumService.removeSubnet(access, subnetId);
				return JSONUtil.jsonSuccess(subnet,
						OpenstackUtil.getMessage("update.success"));
			} else {
				log.debug("not find subnet id:" + subnetId);
				return JSONUtil.jsonError("not find subnet id:" + subnetId);
			}

		} catch (OpenstackAPIException e) {
			log.error(e.getMessage(), e);
			return JSONUtil.jsonError(subnet,
					OpenstackUtil.getMessage("update.failed"));
		}

	}
}
