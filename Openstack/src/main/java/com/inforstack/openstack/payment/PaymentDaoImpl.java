package com.inforstack.openstack.payment;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.StringUtil;

@Repository
public class PaymentDaoImpl extends BasicDaoImpl<Payment> implements PaymentDao {
	
	private static final Logger log = new Logger(PaymentDaoImpl.class);

	@Override
	public List<Payment> findAvailablePayments(Tenant tenant, String paymentUuid) {		
		log.debug("getting available payments for tenant : " + tenant.getId() + ", uuid : " + paymentUuid);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Payment> criteria = builder.createQuery(Payment.class);
			Root<Payment> root = criteria.from(Payment.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("status"), Constants.PAYMENT_STATUS_OK));
			if(tenant != null){
				predicates.add(builder.equal(root.get("tenant.id"), tenant.getId()));
			}
			if(!StringUtil.isNullOrEmpty(paymentUuid)){
				predicates.add(builder.equal(root.get("uuid"), paymentUuid));
			}
			criteria.select(root).where(
				builder.and(predicates.toArray(new Predicate[predicates.size()]))
			).orderBy(
				builder.asc(root.get("createTime"))
			);
			
			List<Payment> list = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(list)){
				log.debug("No instance found");
			}else{
				log.debug(list.size() + " instances found");
			}
			return list;
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
}
