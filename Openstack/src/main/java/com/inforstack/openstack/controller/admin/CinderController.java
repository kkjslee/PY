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
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/admin/cinder")
public class CinderController {

	private static final Logger log = new Logger(CinderController.class);

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private CinderService cinderService;

	private final String CINDER_MODULE_HOME = "admin/modules/Cinder";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return CINDER_MODULE_HOME + "/index";
	}

	@RequestMapping(value = "/volumeTypeList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<VolumeTypeModel> listVolumeTypes(Model model) {
		return this.getVolumeTypes();
	}

	@RequestMapping(value = "/getPagerVolumeTypeList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerVolumeTypeList(HttpServletRequest request,
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

		List<VolumeTypeModel> vtmList = this.getVolumeTypes();

		PagerModel<VolumeTypeModel> page = new PagerModel<VolumeTypeModel>(
				vtmList, pageSze);
		vtmList = page.getPagedData(pageIdx);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.shared", "[plain]");
		conf.put("shared.value", "{shareDisplay} ");
		conf.put(".datas", vtmList);
		conf.put(".forPager", true);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil
				.getJspPage(
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

	private List<VolumeTypeModel> getVolumeTypes() {
		List<VolumeTypeModel> vtmList = new ArrayList<VolumeTypeModel>();
		try {
			VolumeType[] volumeType = this.cinderService.listVolumeTypes();
			log.debug("listing volume types");

			for (VolumeType vt : volumeType) {
				VolumeTypeModel vtModel = new VolumeTypeModel();
				vtModel.setId(vt.getId());
				vtModel.setName(vt.getName());
				vtmList.add(vtModel);
			}
		} catch (OpenstackAPIException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return vtmList;
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
						null, null);

		for (Instance instance : instanceList) {
			String uuid = instance.getUuid();
			VolumeInstance volume = this.instanceService
					.findVolumeInstanceFromUUID(uuid);
			VolumeModel volumeModel = new VolumeModel();
			volumeModel.setId(uuid);
			volumeModel.setName(volume.getName());
			volumeModel.setSize(volume.getSize());
			volumeModel.setCreated(instance.getCreateTime());
			volumeModel.setZone(instance.getRegion());
			volumeModel.setStatus(instance.getStatus());
			volumeModel.setSubOrderId(instance.getSubOrders().get(0).getId());

			VirtualMachine vm = this.instanceService
					.findVirtualMachineFromUUID(volume.getVm());
			if (vm != null) {
				AttachmentModel attachment = new AttachmentModel();
				attachment.setVolume(volume.getUuid());
				attachment.setServer(vm.getName());
				volumeModel.setAttachment(attachment);
			}
			vtList.add(volumeModel);
		}

		PagerModel<VolumeModel> page = new PagerModel<VolumeModel>(vtList,
				pageSze);
		vtList = page.getPagedData(pageIdx);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.name", "[plain]");
		conf.put("grid.size", "[plain]");
		conf.put("size.value", "{size}GB ");
		conf.put("grid.status", "[plain]");
		conf.put("grid.attachTo", "[plain]");
		conf.put("attachTo.value", "{attachment.server} ");
		conf.put(".datas", vtList);
		conf.put(".forPager", true);

		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil
				.getJspPage(
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
