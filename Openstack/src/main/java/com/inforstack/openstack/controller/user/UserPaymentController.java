package com.inforstack.openstack.controller.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.payment.PaymentService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value="/user/pay")
public class UserPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/payInvoice", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> payInvoice(int invoiceId){
		try{
			BigDecimal balance = paymentService.applyPayment(invoiceId);
			return OpenstackUtil.buildSuccessResponse(balance.doubleValue());
		}catch(ApplicationRuntimeException are){
			return OpenstackUtil.buildErrorResponse(are.getMessage());
		}catch(RuntimeException re){
			return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("invoice.pay.failed"));
		}
	}
	
}
