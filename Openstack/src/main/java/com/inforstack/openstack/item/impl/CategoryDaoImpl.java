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

import com.inforstack.openstack.item.Category;
import com.inforstack.openstack.item.CategoryDao;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	
	private static final Log log = LogFactory.getLog(CategoryDaoImpl.class);
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Category> list() {
		List<Category> list = null;
		log.debug("getting all Category instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
			Root<Category> root = criteria.from(Category.class);
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
	public Category findById(int id) {
		Category instance = null;
		log.debug("getting Category instance with id: " + id);
		try {
			instance = em.find(Category.class, id);
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
	public Category findByName(String name) {
		Category intance = null;
		log.debug("getting Category instance with name : " + name);
		if (name != null) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
				Root<Category> root = criteria.from(Category.class);
				criteria.select(root).where(builder.equal(root.get("name"), name));;
				List<Category> instances = em.createQuery(criteria).getResultList();
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
	public Category persist(Category category) {
		log.debug("persist category : " + category.getName());
		try {
			em.persist(category);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		return category;
	}

	@Override
	public void update(Category category) {
		log.debug("update category : " + category.getName());
		try {
			em.merge(category);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(Category category) {
		log.debug("remove category : " + category.getName());
		try {
			em.remove(category);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(int id) {
		Category instance = null;
		log.debug("removing Category instance with id: " + id);
		try {
			instance = em.find(Category.class, id);
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
