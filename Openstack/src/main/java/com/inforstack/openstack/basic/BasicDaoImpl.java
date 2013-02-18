package com.inforstack.openstack.basic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.log.Logger;

public class BasicDaoImpl<T> implements BasicDao<T> {

	private static final Logger log = new Logger(BasicDaoImpl.class);
	
	@Autowired
	protected EntityManager em;
	
	private Class<T> clz;
	
	@SuppressWarnings("unchecked")
	public BasicDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			this.clz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}
	
	public final CriteriaBuilder createBuilder(){
		return em.getCriteriaBuilder();
	}
	
	@Override
	public final PaginationModel<T> pagination(int pageIndex, int pageSize){
		return pagination(pageIndex, pageSize, null, null);
	}
	
	@Override
	public final PaginationModel<T> pagination(int pageIndex, int pageSize, Predicate where,
			Order[] orders){
		PaginationModel<T> model = new PaginationModel<T>();
		log.debug("getting all " + this.clz.getSimpleName() + " instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> count = builder.createQuery(Long.class);
			Root<T> root = count.from(this.clz);
			count.select(builder.count(root));
			if(where != null){
				count.where(where);
			}
			if(orders != null && orders.length>0){
				count.orderBy(orders);
			}
			Long total = em.createQuery(count).getSingleResult();
			
			CriteriaQuery<T> criteria = builder.createQuery(this.clz);
			root = criteria.from(this.clz);
			criteria.select(root);
			if(where != null){
				count.where(where);
			}
			if(orders != null && orders.length>0){
				count.orderBy(orders);
			}
			TypedQuery<T> query = em.createQuery(criteria);
			query.setFirstResult(pageIndex * pageSize);
			query.setMaxResults(pageSize);
			List<T> list = query.getResultList();
			
			log.debug("get successful");
			model.setPageIndex(pageIndex);
			model.setPageSize(pageSize);
			model.setData(list);
			model.setRecordTotal(total.intValue());
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		
		return model;
	}

	@Override
	public final List<T> list() {		
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
	public final T findById(Object primaryKey) {
		T instance = null;
		log.debug("getting " + this.clz.getSimpleName() + " instance with id: " + primaryKey);
		try {
			instance = em.find(this.clz, primaryKey);
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
	public final T findByObject(String name, Object value) {
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
	public final T persist(T instance) {
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
	public final void update(T instance) {
		log.debug("update " + this.clz.getSimpleName());
		try {
			em.merge(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public final void remove(T instance) {
		log.debug("remove " + this.clz.getSimpleName());
		try {
			em.remove(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

}
