package com.inforstack.openstack.billing.process;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BillingProcessDaoImpl implements BillingProcessDao {

	private static final Log log = LogFactory.getLog(BillingProcessDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(BillingProcess billingProcess) {
		log.debug("Persist billing process");
		try {
			em.persist(billingProcess);
			log.debug("Persist billing proccess successfully");
		} catch (RuntimeException re) {
			log.debug("Persist billing process failed", re);
			throw re;
		}
	}

	@Override
	public BillingProcess findById(Integer billingProcessId) {
		log.debug("Find billing process by id : " + billingProcessId);
		
		BillingProcess billingProcess = null;
		try {
			billingProcess = em.find(BillingProcess.class, billingProcessId);
		} catch (RuntimeException re) {
			log.debug("Find billing process failed", re);
			throw re;
		}
		
		if(billingProcess == null){
			log.debug("No billing process failed");
		}else{
			log.debug("Find billing proccess successfully");
		}
		
		return billingProcess;
	}
	
}
