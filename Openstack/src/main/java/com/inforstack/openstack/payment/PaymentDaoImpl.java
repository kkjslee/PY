package com.inforstack.openstack.payment;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl implements PaymentDao {
	
	private static final Log log = LogFactory.getLog(PaymentDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(Payment payment) {
		log.debug("Persist payment");
		try{
			em.persist(payment);
			log.debug("Persist payment successfully");
		}catch(RuntimeException re){
			log.error("Persist payment failed", re);
			throw re;
		}
	}

	@Override
	public Payment findById(Integer paymentId) {
		log.debug("Find payment by id : " + paymentId);
		
		Payment payment = null;
		try {
			payment = em.find(Payment.class, paymentId);
		} catch (RuntimeException re) {
			log.error("Find payment failed", re);
			throw re;
		}
		
		if(payment == null){
			log.debug("No payment found");
		}else{
			log.debug("Find payment successfully");
		}
		
		return payment;
	}

}
