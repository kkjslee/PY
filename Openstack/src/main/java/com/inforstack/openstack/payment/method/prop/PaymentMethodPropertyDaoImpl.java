package com.inforstack.openstack.payment.method.prop;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.utils.Constants;

@Repository
public class PaymentMethodPropertyDaoImpl extends
		BasicDaoImpl<PaymentMethodProperty> implements PaymentMethodPropertyDao {

	@Override
	public List<PaymentMethodProperty> findbyMethodAndType(
			Integer paymentMethodId, Integer type) {
		log.debug("find PaymentMethodProperty by method id : " + paymentMethodId + ", type : " + type);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<PaymentMethodProperty> criteria = builder
					.createQuery(PaymentMethodProperty.class);
			Root<PaymentMethodProperty> root = criteria.from(PaymentMethodProperty.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if(paymentMethodId != null){
				predicates.add(builder.equal(root.get("method").get("id"), paymentMethodId));
			}
			if(type != null){
				predicates.add(builder.equal(root.get("type"), type));
			}
			
			criteria.select(root).where(
					builder.and(
						predicates.toArray(new Predicate[predicates.size()])
					)
			);
			List<PaymentMethodProperty> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances;
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
