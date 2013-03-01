package com.inforstack.openstack.order.sub;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.StringUtil;

@Repository
public class SubOrderDaoImpl extends BasicDaoImpl<SubOrder> implements SubOrderDao {

	private static final Logger log = new Logger(SubOrderDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public List<SubOrder> find(String orderId, Integer status, Integer periodId) {
		log.debug("Find all sub order(s) by order id : " + orderId + ", status : " + status + ", period id : " + periodId);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SubOrder> criteria = builder
					.createQuery(SubOrder.class);
			Root<SubOrder> root = criteria.from(SubOrder.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(!StringUtil.isNullOrEmpty(orderId)){
				predicates.add(
						builder.equal(root.get("order").get("id"), orderId)
				);
			}
			if(status != null){
				predicates.add(
						builder.equal(root.get("status"), status)
				);
			}
			if(periodId != null){
				predicates.add(
						builder.equal(root.get("orderPeriod").get("id"), periodId)
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
	public SubOrder fetchOneByInstanceId(int instanceId) {
		log.debug("Find one sub order by instance id : " + instanceId);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SubOrder> criteria = builder
					.createQuery(SubOrder.class);
			Root<SubOrder> root = criteria.from(SubOrder.class);
			criteria.select(root).where(
					builder.equal(root.get("instance").get("id"), instanceId)
			);
			
			List<SubOrder> instances = em.createQuery(criteria).setMaxResults(1).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return null;
			}
			log.debug("Find successfully");
			return instances.get(0);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
