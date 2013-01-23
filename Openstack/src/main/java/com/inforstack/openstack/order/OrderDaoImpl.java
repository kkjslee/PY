package com.inforstack.openstack.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.utils.CollectionUtil;

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

	@Override
	public Order findById(String orderId) {
		log.debug("Find order by id : " + orderId);
		Order order = null;
		try{
			order = em.find(Order.class, orderId);
		}catch(RuntimeException re){
			log.error("Find order failed", re);
			throw re;
		}
		
		if(order == null){
			log.debug("No order instance found");
		}else{
			log.debug("Find order successfully");
		}
		
		return order;
	}

	@Override
	public List<Order> find(Integer tenantId, Integer status) {
		log.debug("Find all order(s) by tenant id : " + tenantId + ", status : " + status);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Order> criteria = builder
					.createQuery(Order.class);
			Root<Order> root = criteria.from(Order.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(tenantId != null){
				predicates.add(
						builder.equal(root.get("tenant.id"), tenantId)
				);
			}
			if(status != null){
				predicates.add(
						builder.equal(root.get("status"), status)
				);
			}
			if(predicates.isEmpty()){
				criteria.select(root);
			}else{
				criteria.select(root).where(
					builder.and(predicates.toArray(new Predicate[predicates.size()]))
				);
			}
			List<Order> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return instances;
			}
			log.debug("Find successful");
			return instances;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}
	
}
