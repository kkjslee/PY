package com.inforstack.openstack.controller.user;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/user/order")
public class UserOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/list", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> list(int pageIndex, int pageSize, Model model, HttpServletRequest request, HttpServletResponse response){
		PaginationModel<Order> pm = orderService.findAllWithCreator(
				pageIndex, pageSize, SecurityUtils.getTenantId(), null);

		Map<String, String> conf = new LinkedHashMap<String, String>();
		conf.put("grid.id", "[plain]");
		conf.put("id.label", OpenstackUtil.getMessage("order.label"));
		conf.put("grid.amount", "[plain]");
		conf.put("grid.banlance", "[plain]");
		conf.put("grid.autoPay", "[plain]");
		conf.put("grid.status", "[plain]");
		conf.put("grid.createdBy", "[plain]");
		conf.put("createdBy.value", "{createdBy.username}");
		conf.put("grid.createTime", "[plain]");

		model.addAttribute("orders", pm.getData());
		model.addAttribute("configuration", conf);

		String jspString = OpenstackUtil.getJspPage(
				"/templates/grid.jsp?grid.configuration=configuration&type=",
				model.asMap(), request, response);

		if (jspString == null) {
			return OpenstackUtil.buildErrorResponse(OpenstackUtil
					.getMessage("order.list.loading.failed"));
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("recordTotal", pm.getRecordTotal());
			result.put("html", jspString);

			return OpenstackUtil.buildSuccessResponse(result);
		}
	}
}