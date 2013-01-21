package com.inforstack.openstack.item.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.item.Price;
import com.inforstack.openstack.item.PriceDao;

@Repository
public class PriceDaoImpl implements PriceDao {
	
	private static final Log log = LogFactory.getLog(PriceDaoImpl.class);
	
	@Autowired
	private EntityManager em;

	@Override
	public Price findById(int id) {
		Price instance = null;
		log.debug("getting Price instance with id: " + id);
		try {
			instance = em.find(Price.class, id);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
		}
		if (instance == null) {
			log.debug("get failed");
		} else {
			log.debug("get successful");
		}
		return instance;
	}

	@Override
	public Price findByItemSpecificationId(int id) {
		Price intance = null;
		log.debug("getting Category instance with ItemSpecification ID : " + id);
		if (id > 0) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Price> criteria = builder.createQuery(Price.class);
				Root<Price> root = criteria.from(Price.class);
				criteria.select(root).where(builder.equal(root.get("item_id"), id));;
				List<Price> instances = em.createQuery(criteria).getResultList();
				if (instances!=null && instances.size()>0) {
					log.debug("get successful");
					intance = instances.get(0);
				}
				log.debug("No record found for ItemSpecification ID : " + id);
			} catch (RuntimeException re) {
				log.error(re.getMessage(), re);
			}
		}
		return intance;
	}

	@Override
	public Price persist(Price price) {
		try {
			em.persist(price);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		return price;
	}

	@Override
	public void update(Price price) {
		try {
			em.merge(price);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(Price price) {
		try {
			em.remove(price);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(int id) {
		Price instance = null;
		log.debug("removing Price instance with id: " + id);
		try {
			instance = em.find(Price.class, id);
			if (instance != null) {
				em.remove(instance);
				log.debug("remove successful");
			} else {
				log.debug("remove failed");
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
		}
	}

}
