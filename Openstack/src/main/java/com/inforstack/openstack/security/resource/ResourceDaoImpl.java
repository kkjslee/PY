package com.inforstack.openstack.security.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ResourceDaoImpl implements ResourceDao {
	
	private static final Log log = LogFactory.getLog(ResourceDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public List<Resource> listAll() {
		log.debug("getting all Resource instance");
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Resource> criteria = builder
					.createQuery(Resource.class);
			Root<Resource> user = criteria.from(Resource.class);
			criteria.select(user);
			List<Resource> instances = em.createQuery(criteria).getResultList();
			log.debug("get successful");
			return instances;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
