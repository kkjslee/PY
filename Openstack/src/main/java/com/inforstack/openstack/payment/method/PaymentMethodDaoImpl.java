package com.inforstack.openstack.payment.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class PaymentMethodDaoImpl extends BasicDaoImpl<PaymentMethod> implements PaymentMethodDao {

	@Override
	public List<PaymentMethod> findMethodsByCatlogs(int[] catlogs) {
		log.debug("getting payment methods by catlogs : " + Arrays.toString(catlogs));
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<PaymentMethod> criteria = builder.createQuery(PaymentMethod.class);
			Root<PaymentMethod> root = criteria.from(PaymentMethod.class);
			if(catlogs == null){
				criteria.select(root);
			}else{
				criteria.select(root).where(root.get("catlog").in(catlogs));
			}
			
			List<PaymentMethod> list = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(list)){
				log.debug("No instance found");
				return new ArrayList<PaymentMethod>();
			}else{
				log.debug(list.size() + " instances found");
				return list;
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	
	}
	
}
