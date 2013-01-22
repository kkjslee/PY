package com.inforstack.openstack.order;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {

	private static final Log log = LogFactory.getLog(OrderDaoImpl.class);
	
	@Autowired
	private EntityManager em;

	@Override
	public void persist(Order order) {
		log.debug("Persist order");
		try{
			em.persist(order);
			log.debug("Persist order successfully");
		}catch(RuntimeException re){
			log.error("Persist order failed", re);
			throw re;
		}
		
	}

	@Override
	public void merge(Order order) {
		log.debug("Merge order");
		try{
			em.merge(order);
			log.debug("Merge order successfully");
		}catch(RuntimeException re){
			log.error("Merge order failed", re);
			throw re;
		}
		
	}
	
}
