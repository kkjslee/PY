package com.inforstack.openstack.utils.db;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

@Repository
public class AbstractDao<T> implements IDao<T> {

	protected static final Log log = LogFactory.getLog(AbstractDao.class);
	
	@Autowired
	protected EntityManager em;
	
	private Class<T> clz;
	
	@SuppressWarnings("unchecked")
	public AbstractDao() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			this.clz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}

	@Override
	public List<T> list() {		
		List<T> list = null;
		log.debug("getting all " + this.clz.getSimpleName() + " instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(this.clz);
			Root<T> root = criteria.from(this.clz);
			criteria.select(root);
			list = em.createQuery(criteria).getResultList();
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}

	@Override
	public T findById(int id) {
		T instance = null;
		log.debug("getting " + this.clz.getSimpleName() + " instance with id: " + id);
		try {
			instance = em.find(this.clz, id);
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
	public T findByObject(String name, Object value) {
		T intance = null;
		log.debug("getting " + this.clz.getSimpleName() + " instance with " + name + ": " + value.toString());
		if (name != null) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<T> criteria = builder.createQuery(this.clz);
				Root<T> root = criteria.from(this.clz);
				criteria.select(root).where(builder.equal(root.get(name), value));;
				List<T> instances = em.createQuery(criteria).getResultList();
				if (instances != null && instances.size() > 0) {
					log.debug("get successful");
					intance = instances.get(0);
				} else {
					log.debug("No record found");
				}
			} catch (RuntimeException re) {
				log.error(re.getMessage(), re);
			}
		}
		return intance;
	}

	@Override
	public T persist(T instance) {
		log.debug("persist " + this.clz.getSimpleName());
		try {
			em.persist(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		return instance;
	}

	@Override
	public void update(T instance) {
		log.debug("update " + this.clz.getSimpleName());
		try {
			em.merge(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(T instance) {
		log.debug("remove " + this.clz.getSimpleName());
		try {
			em.remove(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(int id) {
		T instance = null;
		log.debug("removing " + this.clz.getSimpleName() + " instance with id: " + id);
		try {
			instance = em.find(this.clz, id);
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
