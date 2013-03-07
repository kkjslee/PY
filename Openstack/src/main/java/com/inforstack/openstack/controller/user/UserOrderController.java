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
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.i18n.dict.DictionaryService;
import com.inforstack.openstack.order.Order;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/user/order")
public class UserOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private DictionaryService dictionaryService;
	private final String ORDER_MODULE_HOME = "user/modules/Order";

	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return ORDER_MODULE_HOME + "/index";

	}

	@RequestMapping(value = "/getPagerOrderList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerOrderList(int pageIndex, int pageSize,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		PaginationModel<Order> pm = orderService.findAllWithCreator(pageIndex,
				pageSize, SecurityUtils.getTenantId(), null);

		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.id", "[plain]");
		conf.put("id.label", OpenstackUtil.getMessage("order.label"));
		conf.put("grid.amount", "[plain]");
		conf.put("grid.balance", "[plain]");
		conf.put("grid.autoPay", "[dict]");
		conf.put("autoPay.options", dictionaryService
				.findDictsByKeyAndLanguageId(
						Constants.DICTIONARY_KEY_TRUE_FALSE, OpenstackUtil
								.getLanguage().getId()));
		conf.put("grid.status", "[dict]");
		conf.put("status.options", dictionaryService
				.findDictsByKeyAndLanguageId(
						Constants.DICTIONARY_KEY_ORDER_STATUS, OpenstackUtil
								.getLanguage().getId()));
		conf.put("grid.createdBy", "[plain]");
		conf.put("createdBy.value", "{createdBy.username} ");
		conf.put("grid.createTime", "[plain]");
		conf.put(".forPager", true);

		conf.put(".datas", pm.getData());

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
			result.put("recordTotal", pm.getRecordTotal());
			result.put("html", jspString);

			return OpenstackUtil.buildSuccessResponse(result);
		}
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> pay(String orderId, int paymentMethodId, HttpServletRequest request){
		try{
			Map<String, Object> property = new HashMap<String, Object>();
			property.put(Constants.PAYMENTMETHODPROPERTY_NAME_HOST, request.getContextPath());
			String endpoint = orderService.payOrder(orderId, paymentMethodId, property);
			return OpenstackUtil.buildSuccessResponse(endpoint);
		}catch(ApplicationRuntimeException are){
			return OpenstackUtil.buildErrorResponse(are.getMessage());
		}catch(RuntimeException re){
			return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("order.pay.failed"));
		}
	}
}
