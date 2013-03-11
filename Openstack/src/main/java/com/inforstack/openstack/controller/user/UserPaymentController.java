package com.inforstack.openstack.controller.user;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inforstack.openstack.billing.invoice.InvoiceService;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.order.OrderService;
import com.inforstack.openstack.payment.Payment;
import com.inforstack.openstack.payment.PaymentService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Controller
@RequestMapping(value="/user/pay")
public class UserPaymentController {
	
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private InvoiceService invoiceService;
	
	private static final String USER_BASE_HOME = "user/modules/Userinfo/";
	
	@RequestMapping(value = "/accountpay", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> accountpay(int trade_id){
		try{
			Payment payment = paymentService.findPaymentById(trade_id);
			if(payment == null){
				return OpenstackUtil.buildErrorResponse(
						OpenstackUtil.getMessage("trade.not.found") + " : " + trade_id);
			}
			
			payment = paymentService.processPayment(payment.getId());
			if(Constants.PAYMENT_STATUS_OK.equals(payment.getStatus())){
				return OpenstackUtil.buildSuccessResponse("");
			}else{
				return OpenstackUtil.buildErrorResponse("");
			}
		}catch(ApplicationRuntimeException are){
			return OpenstackUtil.buildErrorResponse(are.getMessage());
		}catch(RuntimeException re){
			return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("trade.failed"));
		}
	}
	
	@RequestMapping(value = "/alipay", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> alipay(int trade_id){
		try{
			Payment payment = paymentService.findPaymentById(trade_id);
			if(payment == null){
				return OpenstackUtil.buildErrorResponse(
						OpenstackUtil.getMessage("trade.not.found") + " : " + trade_id);
			}
			
			paymentService.topup(payment.getId());
			
			payment = paymentService.processPayment(payment.getId());
			if(Constants.PAYMENT_STATUS_OK.equals(payment.getStatus())){
				return OpenstackUtil.buildSuccessResponse("");
			}else{
				return OpenstackUtil.buildErrorResponse("");
			}
		}catch(ApplicationRuntimeException are){
			return OpenstackUtil.buildErrorResponse(are.getMessage());
		}catch(RuntimeException re){
			return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("trade.failed"));
		}
	}
	
	@RequestMapping(value = "/topup", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, Object> topup(double price){
		try{
			Payment payment = paymentService.topup("topup", new BigDecimal(price));
			
			if(payment!=null && Constants.PAYMENT_STATUS_OK.equals(payment.getStatus())){
				return OpenstackUtil.buildSuccessResponse("OK");
			}else{
				return OpenstackUtil.buildErrorResponse("ERROR");
			}
		}catch(ApplicationRuntimeException are){
			return OpenstackUtil.buildErrorResponse(are.getMessage());
		}catch(RuntimeException re){
			return OpenstackUtil.buildErrorResponse(OpenstackUtil.getMessage("trade.failed"));
		}
	}
	
	@RequestMapping(value = "/showPay", method = RequestMethod.GET)
	public String doPay(Model model) {
		return USER_BASE_HOME + "dopay";
	}
}
