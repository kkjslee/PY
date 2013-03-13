package com.inforstack.openstack.basic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.inforstack.openstack.controller.model.PaginationModel;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.DateUtil;

public class BasicDaoImpl<T> implements BasicDao<T> {

	protected static final Logger log = new Logger(BasicDaoImpl.class);
	
	@Autowired
	protected EntityManager em;
	
	private Class<T> modelClz;
	
	@SuppressWarnings("unchecked")
	public BasicDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			this.modelClz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}
	
	@Override
	public void refresh(Object entity){
		em.refresh(entity);
	}
	
	@Override
	public final PaginationModel<T> pagination(int pageIndex, int pageSize){
		return this.pagination(pageIndex, pageSize, null);
	}
	
	@Override
	public final PaginationModel<T> pagination(int pageIndex, int pageSize, CriteriaQuery<T> query){
		PaginationModel<T> model = new PaginationModel<T>();
		log.debug("getting all " + this.modelClz.getSimpleName() + " instance");
		
		try {
			Root<?> passedRoot = query.getRoots().iterator().next();
			String alias = passedRoot.getAlias();
			if(alias == null){
				alias = this.modelClz.getSimpleName();
				passedRoot.alias(alias);
			}
			
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> count = builder.createQuery(Long.class);
			Root<T> root = count.from(this.modelClz);
			root.alias(alias);
			count.select(builder.count(root));
			Predicate where = query==null? null : query.getRestriction();
			if(where != null){
				count.where(where);
			}
			Long total = em.createQuery(count).getSingleResult();
			
			TypedQuery<T> typedQuery = em.createQuery(query);
			typedQuery.setFirstResult(pageIndex * pageSize);
			typedQuery.setMaxResults(pageSize);
			List<T> list = typedQuery.getResultList();
			
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
	public final List<T> listAll() {		
		List<T> list = null;
		log.debug("getting all " + this.modelClz.getSimpleName() + " instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(this.modelClz);
			Root<T> root = criteria.from(this.modelClz);
			criteria.select(root);
			list = em.createQuery(criteria).getResultList();
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}
	
	public List<T> listByObject(String name, Object value) {
		List<T> list = null;
		log.debug("getting all " + this.modelClz.getSimpleName() + " instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(this.modelClz);
			Root<T> root = criteria.from(this.modelClz);
			criteria.select(root).where(builder.equal(buildPath(root, name), value));
			list = em.createQuery(criteria).getResultList();
			log.debug("get successful");
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}
	
	private Path<T> buildPath(Root<T> root, String name){
		Path<T> ret = null;
		for(String split : name.split("\\.")){
			if(ret == null){
				ret = root.get(split);
			}else{
				ret = ret.get(split);
			}
		}
		
		return ret;
	}

	@Override
	public final T findById(Object primaryKey) {
		T instance = null;
		log.debug("getting " + this.modelClz.getSimpleName() + " instance with id: " + primaryKey);
		try {
			instance = em.find(this.modelClz, primaryKey);
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
		log.debug("getting " + this.modelClz.getSimpleName() + " instance with " + name + ": " + value.toString());
		if (name != null) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<T> criteria = builder.createQuery(this.modelClz);
				Root<T> root = criteria.from(this.modelClz);
				criteria.select(root).where(builder.equal(buildPath(root, name), value));
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
	public final T findLastestBySequenceDate(String column, Date date) {
		log.debug("find lastest "+this.modelClz.getSimpleName()+" by sequence column : " + column + ", date : " + date);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder
					.createQuery(this.modelClz);
			Root<T> root = criteria.from(this.modelClz);
			
			criteria.select(root).where(
					builder.like(root.<String>get(column), DateUtil.getSequenceDate(date)+"%")
			);
			TypedQuery<T> query = em.createQuery(criteria);
			query.setMaxResults(1);
			List<T> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public final T persist(T instance) {
		log.debug("persist " + this.modelClz.getSimpleName());
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
		log.debug("update " + this.modelClz.getSimpleName());
		try {
			em.merge(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public final void remove(T instance) {
		log.debug("remove " + this.modelClz.getSimpleName());
		try {
			em.remove(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	} 
	
	@Override
	public final void lock(Object instance, LockModeType lockType){
		log.debug("lock instance " + this.modelClz.getSimpleName());
		try {
			em.lock(instance, lockType);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}
	
	@Override
	public final void detach(Object instance){
		log.debug("lock instance " + this.modelClz.getSimpleName());
		try {
			em.detach(instance);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}
	
	@Override
	public final void flush(){
		try{
			em.flush();
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}
	
	public static class CursorResult<K>{
		private ScrollableResults results;
		private Session session;
		private K cache;
		
		public CursorResult(ScrollableResults results, Session session){
			results.beforeFirst();
			this.results = results;
			this.session = session;
		}
		
		public boolean hasNext(){
			return results.next();
		}
		
		public K getCurrent(){
			return cache;
		}
		
		@SuppressWarnings("unchecked")
		public K getNext(){
			clearCache();
			setCache((K)results.get(0));
			return getCache();
		}
		
		public void close(){
			clearCache();
			results.close();
		}
		
		private void clearCache(){
			if(cache != null){
				session.evict(cache);
			}
		}
		
		private void setCache(K cache){
			this.cache = cache;
		}
		
		private K getCache() {
			return this.cache;
		}
	}
}
