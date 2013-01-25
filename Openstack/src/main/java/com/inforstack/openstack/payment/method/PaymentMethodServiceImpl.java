package com.inforstack.openstack.payment.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private static final Log log = LogFactory.getLog(PaymentMethodServiceImpl.class);
	@Autowired
	private PaymentMethodDao paymentMethodDao;
	
	@Override
	public PaymentMethod findPaymentMethodById(Integer paymentMethodId) {
		if(paymentMethodId == null){
			log.info("Find payment method failed for passed id is null");
			return null;
		}
		
		log.debug("Find payment method by id : " + paymentMethodId);
		PaymentMethod paymentMethod = paymentMethodDao.findById(paymentMethodId);
		log.debug("Find payment method successfully");
		
		return paymentMethod;
	}
	
}
