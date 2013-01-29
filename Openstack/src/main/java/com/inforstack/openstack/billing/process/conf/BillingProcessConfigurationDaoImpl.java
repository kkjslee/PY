package com.inforstack.openstack.billing.process.conf;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class BillingProcessConfigurationDaoImpl implements
		BillingProcessConfigurationDao {
	
	private static final Log log = LogFactory.getLog(BillingProcessConfigurationDaoImpl.class);
	
	@Autowired
	private EntityManager em;

	@Override
	public void persist(BillingProcessConfiguration bpc) {
		log.debug("Create Billing Process Configuration : " + bpc.getName());
		try {
			em.persist(bpc);
			log.debug("Persist billing process configuration successfully");
		} catch (RuntimeException re) {
			log.error("Persist billing process configuration failed", re);
			throw re;
		}
	}

	@Override
	public BillingProcessConfiguration findById(
			Integer billingProcessConfigurationId) {
		log.debug("Find billing process configuration by id : " + billingProcessConfigurationId);
		
		BillingProcessConfiguration bpc = null;
		try {
			bpc = em.find(BillingProcessConfiguration.class, billingProcessConfigurationId);
		} catch (RuntimeException re) {
			log.error("Find billing process configuration failed", re);
			throw re;
		}
		
		if(bpc == null){
			log.debug("No record found");
		}else{
			log.debug("Find successfully");
		}
		
		return bpc;
	}

	@Override
	public void remove(BillingProcessConfiguration bpc) {
		log.debug("Remove Billing Process Configuration : " + bpc.getName());
		try {
			em.remove(bpc);
			log.debug("Remove billing process configuration successfully");
		} catch (RuntimeException re) {
			log.error("Remove billing process configuration failed", re);
			throw re;
		}
	}

	@Override
	public List<BillingProcessConfiguration> findAll() {
		log.debug("Find all billing process configurations");
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BillingProcessConfiguration> criteria = builder
					.createQuery(BillingProcessConfiguration.class);
			Root<BillingProcessConfiguration> root = criteria.from(BillingProcessConfiguration.class);
			criteria.select(root);
			List<BillingProcessConfiguration> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return null;
			}else{
				log.debug( instances.size() + " record(s) found");
				return instances;
			}
			
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
