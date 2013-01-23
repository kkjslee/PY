package com.inforstack.openstack.order.sub;

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
import com.inforstack.openstack.utils.StringUtil;

@Repository
public class SubOrderDaoImpl implements SubOrderDao {

	private static final Log log = LogFactory.getLog(SubOrderDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(SubOrder subOrder) {
		log.debug("Persist sub order");
		try {
			em.persist(subOrder);
			log.debug("Persist sub order successfullly");
		} catch (RuntimeException re) {
			log.error("Persist sub order failed", re);
			throw re;
		}
	}

	@Override
	public List<SubOrder> find(String orderId, Integer status) {
		log.debug("Find all sub order(s) by order id : " + orderId + ", status : " + status);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SubOrder> criteria = builder
					.createQuery(SubOrder.class);
			Root<SubOrder> root = criteria.from(SubOrder.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(!StringUtil.isNullOrEmpty(orderId)){
				predicates.add(
						builder.equal(root.get("order.id"), orderId)
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
			List<SubOrder> instances = em.createQuery(criteria).getResultList();
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

	@Override
	public SubOrder findById(Integer subOrderId) {
		log.debug("Find sub order by id : " + subOrderId);
		
		SubOrder subOrder = null;
		try {
			subOrder = em.find(SubOrder.class, subOrderId);
		} catch (RuntimeException re) {
			log.error("Find sub order failed", re);
			throw re;
		}
		
		if(subOrder == null){
			log.debug("No sub order found");
		}else{
			log.debug("Find sub order successfullly");
		}
		
		return subOrder;
	}
	
}
