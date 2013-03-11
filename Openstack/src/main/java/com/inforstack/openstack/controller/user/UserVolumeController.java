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

import com.inforstack.openstack.api.OpenstackAPIException;
import com.inforstack.openstack.api.cinder.CinderService;
import com.inforstack.openstack.api.cinder.VolumeType;
import com.inforstack.openstack.controller.model.AttachmentModel;
import com.inforstack.openstack.controller.model.PagerModel;
import com.inforstack.openstack.controller.model.VolumeModel;
import com.inforstack.openstack.controller.model.VolumeTypeModel;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceService;
import com.inforstack.openstack.instance.VirtualMachine;
import com.inforstack.openstack.instance.VolumeInstance;
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
@RequestMapping(value = "/user/cinder")
public class UserVolumeController {

	private static final Logger log = new Logger(UserVolumeController.class);

	private final String CINDER_MODULE_HOME = "user/modules/Cinder";

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private CinderService cinderService;

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return CINDER_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/modules/template", method = RequestMethod.GET)
	public String template(Model model) {
		return CINDER_MODULE_HOME + "/template";
	}

	@RequestMapping(value = "/getPagerVolumeTypeList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getVolumeTypes(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer pageIndex,
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

		List<VolumeTypeModel> vtmList = new ArrayList<VolumeTypeModel>();
		try {
			VolumeType[] volumeTypes = this.cinderService.listVolumeTypes();
			log.debug("listing volume types");

			for (VolumeType vt : volumeTypes) {
				VolumeTypeModel vtModel = new VolumeTypeModel();
				vtModel.setId(vt.getId());
				vtModel.setName(vt.getName());
				vtmList.add(vtModel);
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}

		PagerModel<VolumeTypeModel> page = new PagerModel<VolumeTypeModel>(
				vtmList, pageSze);
		vtmList = page.getPagedData(pageIdx);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.shared", "[plain]");
		conf.put("shared.value", "{shareDisplay} ");
		conf.put(".forPager", true);

		conf.put(".datas", vtmList);

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

	@RequestMapping(value = "/getPagerVolumeList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerVolumeList(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer pageIndex,
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

		List<VolumeModel> vtList = new ArrayList<VolumeModel>();

		Tenant tenant = SecurityUtils.getTenant();

		List<Instance> instanceList = this.instanceService
				.findInstanceFromTenant(tenant, Constants.INSTANCE_TYPE_VOLUME,
						null, "deleted");

		for (Instance instance : instanceList) {
			String uuid = instance.getUuid();
			VolumeInstance volume = this.instanceService
					.findVolumeInstanceFromUUID(uuid);
			VolumeModel volumeModel = new VolumeModel();
			volumeModel.setId(uuid);
			volumeModel.setName(volume.getName());
			volumeModel.setSize(volume.getSize());
			volumeModel.setCreated(instance.getCreateTime());
			volumeModel.setStatus(instance.getStatus());
			volumeModel.setSubOrderId(instance.getSubOrders().get(0).getId());

			DataCenter dataCenter = this.instanceService
					.getDataCenterFromInstance(instance);
			volumeModel.setZone(dataCenter.getName().getI18nContent());

			String vmId = volume.getVm();
			VirtualMachine vm = null;
			if (vmId != null && !vmId.trim().isEmpty()) {
				vm = this.instanceService.findVirtualMachineFromUUID(vmId);
			}
			if (vm != null) {
				AttachmentModel attachment = new AttachmentModel();
				attachment.setVolume(volume.getUuid());
				attachment.setServer(vm.getName());
				attachment.setId(vm.getUuid());
				volumeModel.setAttachment(attachment);
			} else {
				AttachmentModel attachment = new AttachmentModel();
				attachment.setServer("");
				attachment.setId("");
				volumeModel.setAttachment(attachment);
			}
			vtList.add(volumeModel);
		}

		PagerModel<VolumeModel> page = new PagerModel<VolumeModel>(vtList,
				pageSze);
		vtList = page.getPagedData(pageIdx);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.id", "[hidden]");
		conf.put("grid.statusV", "[hidden]");
		conf.put("statusV.label", " ");
		conf.put("statusV.value", "{status}");
		conf.put("grid.size", "[plain]");
		conf.put("size.value", "{size}GB ");
		conf.put("grid.status", "[plain]");
		conf.put("status.value", "{statusDisplay} ");
		conf.put("grid.attachTo", "[plain]");
		conf.put("grid.zone", "[plain]");
		conf.put("zone.value", "{zone} ");
		conf.put("attachTo.value", "{attachment.server} ");
		conf.put("grid.operation", "[button]attach,detach");
		conf.put("attach.onclick", "showDetachorAttachVolume('attach','"
				+ OpenstackUtil.getMessage("attach.label")
				+ "','{id}','{attachment.id}', this)");
		conf.put("detach.onclick", "showDetachorAttachVolume('detach','"
				+ OpenstackUtil.getMessage("detach.label")
				+ "','{id}','{attachment.id}',this)");
		conf.put(".forPager", true);
		conf.put(".datas", vtList);

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

	@RequestMapping(value = "/volumecontrol", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> controlVolume(Model model, String executecommand,
			String volumeId, String serverId) {
		if (StringUtil.isNullOrEmpty(serverId)) {
			return JSONUtil.jsonError("server is null");
		}
		try {
			if (!StringUtil.isNullOrEmpty(executecommand)
					&& !StringUtils.isNullOrEmpty(volumeId)) {
				User user = SecurityUtils.getUser();
				Tenant tenant = SecurityUtils.getTenant();
				if (executecommand.equals("attach")) {
					this.instanceService.attachVolume(user, tenant, volumeId,
							serverId);
				} else if (executecommand.equals("detach")) {
					this.instanceService.detachVolume(user, tenant, volumeId,
							serverId);
				} else if (executecommand.equals("remove")) {
					this.instanceService.removeVolume(user, tenant, volumeId);
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
	@RequestMapping(value = "/getVolumeDetail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getVolume(Model model, String uuid) {
		Instance vi = instanceService.findInstanceFromUUID(uuid);
		VolumeModel volumeModel = new VolumeModel();
		if (vi != null) {
			volumeModel.setId(vi.getUuid());
			volumeModel.setStatus(vi.getStatus());
			return JSONUtil.jsonSuccess(volumeModel);
		} else {
			return JSONUtil.jsonError("not found");
		}

	}
}
