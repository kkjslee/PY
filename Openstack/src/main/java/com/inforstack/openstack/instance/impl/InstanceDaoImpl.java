package com.inforstack.openstack.instance.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.tenant.Tenant;

@Repository
public class InstanceDaoImpl extends BasicDaoImpl<Instance> implements InstanceDao {

	@Override
	public List<Instance> listInstancesByTenant(Tenant tenant, String includeStatus, String excludeStatus) {
		List<Instance> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Instance> criteria = builder.createQuery(Instance.class);
			Root<Instance> root = criteria.from(Instance.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("subOrder").get("order").get("tenant").get("id"), tenant.getId()));
			if(includeStatus != null){
				predicates.add(
					builder.equal(root.get("status"), includeStatus)
				);
			}
			if (excludeStatus != null) {
				predicates.add(
					builder.notEqual(root.get("status"), excludeStatus)
				);
			}
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			list = em.createQuery(criteria).getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}
	
	@Override
	public List<Instance> listInstancesBySubOrder(Integer subOrderId, String includeStatus, String excludeStatus) {
		List<Instance> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Instance> criteria = builder.createQuery(Instance.class);
			Root<Instance> root = criteria.from(Instance.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("subOrder").get("id"), subOrderId));
			if(includeStatus != null){
				predicates.add(
					builder.equal(root.get("status"), includeStatus)
				);
			}
			if (excludeStatus != null) {
				predicates.add(
					builder.notEqual(root.get("status"), excludeStatus)
				);
			}
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			list = em.createQuery(criteria).getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}

}
