package com.inforstack.openstack.payment.method;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;

@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private static final Logger log = new Logger(PaymentMethodServiceImpl.class);
	@Autowired
	private PaymentMethodDao paymentMethodDao;
	
	@Override
	public PaymentMethod findPaymentMethodById(int paymentMethodId) {
		log.debug("Find payment method by id : " + paymentMethodId);
		PaymentMethod paymentMethod = paymentMethodDao.findById(paymentMethodId);
		log.debug("Find payment method successfully");
		
		return paymentMethod;
	}
	
	@Override
	public List<PaymentMethod> listAll(){
		return paymentMethodDao.listAll();
	}
}
