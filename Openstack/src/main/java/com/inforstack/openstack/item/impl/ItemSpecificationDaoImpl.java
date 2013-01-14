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

import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;

@Repository
public class ItemSpecificationDaoImpl implements ItemSpecificationDao {

	private static final Log log = LogFactory.getLog(ItemSpecificationDaoImpl.class);
	
	@Autowired
	private EntityManager em;
	
	@Override
	public List<ItemSpecification> list() {
		List<ItemSpecification> list = null;
		log.debug("getting all ItemSpecification instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ItemSpecification> criteria = builder.createQuery(ItemSpecification.class);
			Root<ItemSpecification> root = criteria.from(ItemSpecification.class);
			criteria.select(root);
			list = em.createQuery(criteria).getResultList();
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return list;
	}

	@Override
	public ItemSpecification findById(int id) {
		ItemSpecification instance = null;
		log.debug("getting ItemSpecification instance with id: " + id);
		try {
			instance = em.find(ItemSpecification.class, id);
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
	public ItemSpecification findByName(String name) {
		ItemSpecification intance = null;
		log.debug("getting ItemSpecification instance with name : " + name);
		if (name != null) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<ItemSpecification> criteria = builder.createQuery(ItemSpecification.class);
				Root<ItemSpecification> root = criteria.from(ItemSpecification.class);
				criteria.select(root).where(builder.equal(root.get("name"), name));;
				List<ItemSpecification> instances = em.createQuery(criteria).getResultList();
				if (instances!=null && instances.size()>0) {
					log.debug("get successful");
					intance = instances.get(0);
				}
				log.debug("No record found for name : " + name);
			} catch (RuntimeException re) {
				log.error(re.getMessage(), re);
			}
		}
		return intance;
	}

	@Override
	public ItemSpecification persist(ItemSpecification itemSpecification) {
		log.debug("persist itemSpecification : " + itemSpecification.getName());
		try {
			em.persist(itemSpecification);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		return itemSpecification;
	}

	@Override
	public void update(ItemSpecification itemSpecification) {
		log.debug("update itemSpecification : " + itemSpecification.getName());
		try {
			em.merge(itemSpecification);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(ItemSpecification itemSpecification) {
		log.debug("remove ItemSpecification : " + itemSpecification.getName());
		try {
			em.remove(itemSpecification);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(int id) {
		ItemSpecification instance = null;
		log.debug("removing ItemSpecification instance with id: " + id);
		try {
			instance = em.find(ItemSpecification.class, id);
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
