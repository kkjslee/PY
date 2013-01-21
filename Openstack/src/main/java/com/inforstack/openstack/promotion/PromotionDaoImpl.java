package com.inforstack.openstack.promotion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;

@Repository
public class PromotionDaoImpl implements PromotionDao {
	
	private static final Log log = LogFactory.getLog(PromotionDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(Promotion promotion) {
		log.debug("Persist promotion : " + promotion.getName());
		try{
			em.persist(promotion);
			log.debug("Persist successfully");
		}catch(RuntimeException re){
			log.error("Persist failed");
			throw re;
		}
	}

	@Override
	public Promotion findById(Integer id) {
		log.debug("Find promotion by id : " + id);
		
		Promotion promotion = null;
		try{
			promotion = em.find(Promotion.class, id);
		}catch(RuntimeException re){
			log.error("Find promotion failed", re);
			throw re;
		}
		
		if(promotion == null){
			log.debug("Find promotion failed");
		}else{
			log.debug("Fnd promotion successfully");
		}
		
		return promotion;
	}

	@Override
	public Promotion findByName(String name) throws ApplicationException {
		log.debug("Find promotion by name : " + name);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Promotion> criteria = builder
					.createQuery(Promotion.class);
			Root<Promotion> root = criteria.from(Promotion.class);
			criteria.select(root).where(
					builder.and(
							builder.equal(root.get("name"), name),
							builder.equal(root.get("deleted"), false)
					)
			);
			List<Promotion> instances = em.createQuery(criteria).getResultList();
			if(instances==null){
				log.debug("No record found by name : " + name);
				return null;
			}else if(instances.size()==1){
				log.debug("Find successfully");
				return instances.get(0);
			}else{
				throw new ApplicationException("Exception occured for more than one prmotions found with name : " + name);
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}
	
	@Override
	public Promotion findByNameAndTenant(String name, Tenant tenant) throws ApplicationException{
		log.debug("Find promotion by name : " + name + " and tenant : " + tenant.getId());
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Promotion> criteria = builder
					.createQuery(Promotion.class);
			Root<Promotion> root = criteria.from(Promotion.class);
			criteria.select(root).where(
					builder.and(
							builder.equal(root.get("name"), name),
							builder.equal(root.get("tenant"), tenant),
							builder.equal(root.get("deleted"), false)
					)
			);
			List<Promotion> instances = em.createQuery(criteria).getResultList();
			if(instances==null){
				log.debug("No record found");
				return null;
			}else if(instances.size()==1){
				log.debug("Find successfully");
				return instances.get(0);
			}else{
				throw new ApplicationException("Exception occured for more than one prmotions found with name : " + name + " and tenant : " + tenant.getId());
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	
	}

	@Override
	public List<Promotion> findAllByName(String name) {
		log.debug("Find all promotion by name : " + name);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Promotion> criteria = builder
					.createQuery(Promotion.class);
			Root<Promotion> root = criteria.from(Promotion.class);
			criteria.select(root).where(
					builder.and(
							builder.equal(root.get("name"), name)
					)
			);
			List<Promotion> instances = em.createQuery(criteria).getResultList();
			if(instances==null || instances.size()==0){
				log.debug("No record found by name : " + name);
				return null;
			}else {
				log.debug("Find successfully");
				return instances;
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public boolean exist(String name) throws ApplicationException {
		log.debug("Find all promotion by name : " + name);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Promotion> criteria = builder
					.createQuery(Promotion.class);
			Root<Promotion> root = criteria.from(Promotion.class);
			criteria.select(root).where(
					builder.and(
							builder.equal(root.get("name"), name)
					)
			);
			List<Promotion> instances = em.createQuery(criteria).getResultList();
			if(instances==null || instances.size()==0){
				log.debug("No record found by name : " + name);
				return false;
			}else {
				log.debug("Find successfully");
				return true;
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
