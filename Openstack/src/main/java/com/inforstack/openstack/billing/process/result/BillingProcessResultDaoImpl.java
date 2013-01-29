package com.inforstack.openstack.billing.process.result;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BillingProcessResultDaoImpl implements BillingProcessResultDao {
	
	private static final Log log = LogFactory.getLog(BillingProcessResultDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(BillingProcessResult billingProcessResult) {
		log.debug("Persist billing process result");
		try {
			em.persist(billingProcessResult);
			log.debug("Persist billing process result successfully");
		} catch (RuntimeException re) {
			log.error("Persist billing process result failed", re);
			throw re;
		}
	}

	@Override
	public BillingProcessResult findById(int billingProcessId) {
		log.debug("Find billing process result by id : " + billingProcessId);
		BillingProcessResult bpr = null;
		try {
			bpr = em.find(BillingProcessResult.class, billingProcessId);
		} catch (RuntimeException re) {
			log.error("Find instance failed");
			throw re;
		}
		
		if(bpr == null){
			log.debug("No instance found");
		}else{
			log.debug("Find billing process result successfully");
		}
		
		return bpr;
	}

}
