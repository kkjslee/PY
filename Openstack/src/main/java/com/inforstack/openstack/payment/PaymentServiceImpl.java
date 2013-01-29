package com.inforstack.openstack.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.tenant.TenantService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.OpenstackUtil;

@Service(value="paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {
	
	private static final Log log = LogFactory.getLog(PaymentServiceImpl.class);
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private TenantService tenantService;
	
	@Override
	public Payment createPayment(Payment payment) {
		if(payment == null){
			log.info("Create payment failed for passed payment is null");
			return null;
		}
		
		log.debug("Create paymant");
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}
	
	@Override
	public Payment createPayment(Tenant tenant, double amount, boolean isRefund, int type) {
		if(tenant==null){
			log.info("Create payment failed for passed tenant is null");
			return null;
		}
		
		log.debug("Create payment for tenant : " + tenant.getName());
		Payment payment = new Payment();
		BigDecimal amoutBD = new BigDecimal(amount);
		payment.setAmount(amoutBD);
		payment.setBalance(amoutBD);
		payment.setCreateTime(new Date());
		payment.setType(type);
		payment.setStatus(Constants.PAYMENT_STATUS_NEW);
		payment.setTenant(tenant);
		
		paymentDao.persist(payment);
		log.debug("Create payment successfully");
		return payment;
	}

	@Override
	public Payment createPayment(int tenantId, double amount,
			boolean isRefund, int type) {
		log.debug("Create payment for tenant : " + tenantId);
		Tenant tenant = tenantService.findTenantById(tenantId);
		if(tenant == null){
			log.info("Create payment failed for no tenant found by id : " + tenantId);
			return null;
		}
		PaymentService self = (PaymentService)OpenstackUtil.getBean("paymentService");
		Payment payment = self.createPayment(tenant, amount, isRefund, type);
		
		if(payment == null){
			log.debug("Create payment failed");
		}else{
			log.debug("Create payment successfully");
		}
		return payment;
	}

	@Override
	public Payment processPayment(int paymentId) {
		log.debug("Process payment : " + paymentId);
		Payment payment = paymentDao.findById(paymentId);
		if(payment==null){
			log.info("Process payment failed for no payment found by id : " + paymentId);
			return null;
		}
		
		payment.setStatus(Constants.PAYMENT_STATUS_PROCESSING);
		log.debug("Process payment status successfully");
		return payment;
	}
	
}
