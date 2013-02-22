package com.inforstack.openstack.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.inforstack.openstack.api.nova.server.Address;
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
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;
import com.inforstack.openstack.utils.StringUtil;

@Controller
@RequestMapping(value = "/user/instance")
public class InstanceUserController {

	private static final Logger log = new Logger(InstanceUserController.class);

	@Autowired
	private ServerService serverService;

	@Autowired
	private KeystoneService keystoneService;

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

	@RequestMapping(value = "/getPagerInstanceList", method = RequestMethod.POST)
	public String getPagerInstances(Model model, Integer pageIndex,
			Integer pageSize) {
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
			String username = SecurityUtils.getUserName();
			String password = SecurityUtils.getUser().getPassword();
			Tenant tenant = SecurityUtils.getTenant();
			Access access = keystoneService.getAccess(username, password,
					tenant.getUuid(), true);
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
						im.setStatusdisplay(OpenstackUtil.getMessage(server
								.getStatus() + ".status.vm"));
						im.setTenantId(server.getTenant());
						im.setStarttime(server.getCreated());
						im.setUpdatetime(server.getUpdated());
						im.setTaskStatus(server.getTask());
						im.setAssignedto(access.getUser().getUsername());
						im.setAccesspoint("");
						Map<String, Address[]> addresses = server
								.getAddresses();
						Iterator<Entry<String, Address[]>> it = addresses
								.entrySet().iterator();
						Map<String, String> tempAddress = new HashMap<String, String>();
						while (it.hasNext()) {
							Entry<String, Address[]> entry = it.next();
							String key = entry.getKey();
							Address[] values = entry.getValue();
							String ipString = "";
							if (values != null && values.length > 0) {
								int length = values.length;
								for (int i = 0; i < length; i++) {
									ipString = ipString + values[i].getAddr();
									if (i < length - 1) {
										ipString = ipString + ",";
									}
								}
							}

							tempAddress.put(key, ipString);
						}
						im.setAddresses(tempAddress);
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

				PagerModel<InstanceModel> page = new PagerModel<InstanceModel>(
						imList, pageSze);
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
			String username = SecurityUtils.getUserName();
			String password = SecurityUtils.getUser().getPassword();
			Tenant tenant = SecurityUtils.getTenant();
			Access access = keystoneService.getAccess(username, password,
					tenant.getUuid(), true);

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

	@RequestMapping(value = "/getInstance", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	InstanceModel retrieveInstance(Model model, String vmId) {
		InstanceModel im = new InstanceModel();
		if (!StringUtil.isNullOrEmpty(vmId, false)) {
			try {
				String username = SecurityUtils.getUserName();
				String password = SecurityUtils.getUser().getPassword();
				Tenant tenant = SecurityUtils.getTenant();
				Access access = keystoneService.getAccess(username, password,
						tenant.getUuid(), true);

				if (access != null) {
					Server server = serverService
							.getServer(access, vmId, false);
					if (server != null) {
						im.setTaskStatus(server.getTask());
						im.setStatus(server.getStatus());
						im.setVmname(server.getName());
						im.setStatusdisplay(OpenstackUtil.getMessage(server
								.getStatus() + ".status.vm"));
						im.setTenantId(server.getTenant());
						im.setStarttime(server.getCreated());
						im.setUpdatetime(server.getUpdated());
						im.setAssignedto(access.getUser().getUsername());
						Map<String, Address[]> addresses = server
								.getAddresses();
						Iterator<Entry<String, Address[]>> it = addresses
								.entrySet().iterator();
						Map<String, String> tempAddress = new HashMap<String, String>();
						while (it.hasNext()) {
							Entry<String, Address[]> entry = it.next();
							String key = entry.getKey();
							Address[] values = entry.getValue();
							String ipString = "";
							if (values != null && values.length > 0) {
								int length = values.length;
								for (int i = 0; i < length; i++) {
									ipString = ipString + values[i].getAddr();
									if (i < length - 1) {
										ipString = ipString + ",";
									}
								}
							}

							tempAddress.put(key, ipString);
						}
						im.setAddresses(tempAddress);
						Flavor flavor = server.getFlavor();
						if (flavor != null) {
							im.setCpus(flavor.getVcpus());
							im.setMaxcpus(flavor.getVcpus());
							im.setMemory(flavor.getRam());
							im.setMaxmemory(flavor.getRam());
							im.setDisksize(flavor.getDisk());
						}
						im.setStatusdisplay(OpenstackUtil.getMessage(server
								.getStatus() + ".status.vm"));
					}
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
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put(".form", "start_end");
		conf.put("form.vmname", "[plain]" + instance.getVmname());
		conf.put("form.statusDisplay", "[plain]" + instance.getStatusdisplay());
		conf.put("form.imagename", "[plain]" + instance.getImageId());
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
		conf.put("form.addressString", "[plain]" + instance.getAddressString());

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

}
