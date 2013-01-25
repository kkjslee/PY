package com.inforstack.openstack.order.period;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class OrderPeriodDaoImpl implements OrderPeriodDao {
	
	private static final Log log = LogFactory.getLog(OrderPeriodDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(OrderPeriod period) {
		log.debug("Persist period");
		try{
			em.persist(period);
			log.debug("Persist period successfully");
		}catch(RuntimeException re){
			log.error("Persist period failed", re);
			throw re;
		}
	}

	@Override
	public void merge(OrderPeriod period) {
		log.debug("Merge period");
		try{
			em.merge(period);
			log.debug("Merge period successfully");
		}catch(RuntimeException re){
			log.error("Merge period failed", re);
			throw re;
		}
	}

	@Override
	public void remove(OrderPeriod period) {
		log.debug("Remove period");
		try{
			em.remove(period);
			log.debug("Remove period successfully");
		}catch(RuntimeException re){
			log.error("Remove period failed", re);
			throw re;
		}
	}

	@Override
	public OrderPeriod findById(Integer periodId) {
		log.debug("Find period by id : " + periodId);
		
		OrderPeriod period = null;
		try{
			period = em.find(OrderPeriod.class, periodId);
		}catch(RuntimeException re){
			log.error("Find period failed", re);
			throw re;
		}
		
		if(period == null){
			log.debug("No period instance found by id : " + periodId);
		}else{
			log.debug("Find successfully");
		}
		
		return period;
	}

	@Override
	public List<OrderPeriod> findAll(boolean includeDeleted) {
		log.debug("Find all period instance(s)  " + (includeDeleted?"include deleted":"exclude deleted"));
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<OrderPeriod> criteria = builder
					.createQuery(OrderPeriod.class);
			Root<OrderPeriod> root = criteria.from(OrderPeriod.class);
			if(includeDeleted){
				criteria.select(root);
			}else{
				criteria.select(root).where(
						builder.equal(root.get("deleted"), false)
				);
			}
			List<OrderPeriod> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return instances;
			}
			log.debug("get successful");
			return null;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
