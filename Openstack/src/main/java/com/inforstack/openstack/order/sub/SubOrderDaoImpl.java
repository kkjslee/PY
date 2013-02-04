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

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.StringUtil;

@Repository
public class SubOrderDaoImpl extends BasicDaoImpl<SubOrder> implements SubOrderDao {

	private static final Log log = LogFactory.getLog(SubOrderDaoImpl.class);
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
						builder.equal(root.get("order.id"), orderId)
				);
			}
			if(status != null){
				predicates.add(
						builder.equal(root.get("status"), status)
				);
			}
			if(periodId != null){
				predicates.add(
						builder.equal(root.get("orderPeriod.id"), periodId)
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

}
