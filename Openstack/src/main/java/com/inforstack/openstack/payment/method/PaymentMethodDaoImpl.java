package com.inforstack.openstack.payment.method;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentMethodDaoImpl implements PaymentMethodDao {
	
	private static final Log log = LogFactory.getLog(PaymentMethodDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public PaymentMethod findById(Integer paymentMethodId) {
		log.debug("Find payment method by id : " + paymentMethodId);
		
		PaymentMethod paymentMethod = null;
		try{
			paymentMethod = em.find(PaymentMethod.class, paymentMethodId);
		} catch(RuntimeException re){
			log.error("Find payment method failed", re);
			throw re;
		}
		
		if(paymentMethod == null){
			log.debug("No payment method found");
		}else{
			log.debug("Find payment method successfully");
		}
		
		return paymentMethod;
	}
}
