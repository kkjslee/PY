package com.inforstack.openstack.controller.user;

import java.util.Date;
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

import com.inforstack.openstack.billing.invoice.Invoice;
import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.i18n.dict.DictionaryService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.SecurityUtils;

@Controller
@RequestMapping(value = "/user/invoice")
public class UserInvoiceController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
	private final String INVOICE_MODULE_HOME = "user/modules/Invoice";
	
	@RequestMapping(value = "/modules/index", method = RequestMethod.GET)
	public String redirectModule(Model model, HttpServletRequest request) {
		return INVOICE_MODULE_HOME + "/index";
	}
	
	@RequestMapping(value = "/getPagerInvoiceList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, Object> getPagerOrderList(int pageIndex, int pageSize,
			Model model, Date startTime,Date  endTime,HttpServletRequest request,
			HttpServletResponse response) {
		Date end = new Date();
		Date start = new Date(end.getTime() -86400000 * 7);
		PaginationModel<Invoice> im = invoiceService.findInvoice(pageIndex, pageSize, SecurityUtils.getTenantId(),start, end);
		
		Map<String, Object> conf = new LinkedHashMap<String, Object>();
		conf.put("grid.id", "[hidden]");
		conf.put("grid.createTime", "[plain]");
		conf.put("grid.startTime", "[plain]");
		conf.put("grid.endTime", "[plain]");
		conf.put("grid.amount", "[plain]");
		conf.put("grid.balance", "[plain]");
		conf.put("grid.payTime", "[plain]");
		conf.put("grid.orderPeriod", "[plain]");
		conf.put("orderPeriod.value", "{subOrder.orderPeriod.name.i18nContent}");
		conf.put("grid.status", "[dict]");
		conf.put("status.options", dictionaryService
				.findDictsByKeyAndLanguageId(
						Constants.DICTIONARY_KEY_INVOICE_STATUS, OpenstackUtil
								.getLanguage().getId()));
		conf.put("grid.operation", "[button]pay");
		conf.put("pay.onclick", "showPayMethods('{order.id}')");
		
		conf.put(".forPager", true);

		conf.put(".datas", im.getData());

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
			result.put("recordTotal", im.getRecordTotal());
			result.put("html", jspString);

			return OpenstackUtil.buildSuccessResponse(result);
		}
	}
}
