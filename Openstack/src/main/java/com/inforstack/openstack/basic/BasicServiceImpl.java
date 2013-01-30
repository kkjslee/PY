package com.inforstack.openstack.basic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasicServiceImpl<T> implements BasicService<T> {
	
	private static final Log log = LogFactory.getLog(BasicServiceImpl.class);
	@Autowired
	protected BasicDao<T> basicDao;
	
	private Class<T> clz;
	
	@SuppressWarnings("unchecked")
	public BasicServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			this.clz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
		}
	}
	
	@Override
	public T findById(Object primaryKey) {
		log.debug("find " + this.clz.getSimpleName() + " instance by id : " + primaryKey);
		T instance = basicDao.findById(primaryKey);
		if(instance == null){
			log.debug("Find failed");
		}else{
			log.debug("Find successfully");
		}
		
		return instance;
	}

	@Override
	public T create(T instance) {
		log.debug("Create " + this.clz.getSimpleName() + " instance ");
		instance = basicDao.persist(instance);
		if(instance == null){
			log.debug("Create failed");
		}else{
			log.debug("Create successfully");
		}
		
		return instance;
	}

	@Override
	public T update(T instance) {
		log.debug("Update " + this.clz.getSimpleName() + " instance");
		basicDao.update(instance);
		log.debug("Update successfully");
		
		return instance;
	}

	@Override
	public T remove(T instance) {
		log.debug("Remove " + this.clz.getSimpleName() + " instance");
		basicDao.remove(instance);
		log.debug("Remove successfully");
		
		return instance;
	}
	
	@Override
	public final T removeByPrimaryKey(Object primaryKey) {
		log.debug("removing " + this.clz.getSimpleName() + " instance with id: " + primaryKey);
		
		T instance  = basicDao.findById(primaryKey);
		if (instance != null) {
			basicDao.remove(instance);
			log.debug("remove successful");
		} else {
			log.debug("remove failed");
		}
		
		return instance;
	}

}
