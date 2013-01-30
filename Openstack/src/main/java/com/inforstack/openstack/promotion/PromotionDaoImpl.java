package com.inforstack.openstack.promotion;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.exception.ApplicationException;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.db.AbstractDao;

@Repository
public class PromotionDaoImpl extends AbstractDao<Promotion> implements PromotionDao {
	
	private static final Log log = LogFactory.getLog(PromotionDaoImpl.class);
	
	@Override
	public Promotion findByNameAndRole(String name, int roleId) throws ApplicationException {
		log.debug("Find promotion by name : " + name);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Promotion> criteria = builder
					.createQuery(Promotion.class);
			Root<Promotion> root = criteria.from(Promotion.class);
			criteria.select(root).where(
					builder.and(
							builder.equal(root.get("name"), name),
							builder.equal(root.get("roleId"), roleId),
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
