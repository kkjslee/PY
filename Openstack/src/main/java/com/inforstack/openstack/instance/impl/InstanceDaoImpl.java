package com.inforstack.openstack.instance.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.instance.Instance;
import com.inforstack.openstack.instance.InstanceDao;
import com.inforstack.openstack.tenant.Tenant;

@Repository
public class InstanceDaoImpl extends BasicDaoImpl<Instance> implements InstanceDao {

	@Override
	public List<Instance> listInstancesByTenant(Tenant tenant) {
		List<Instance> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Instance> criteria = builder.createQuery(Instance.class);
			Root<Instance> root = criteria.from(Instance.class);
			criteria.select(root).where(builder.equal(root.get("subOrder").get("order").get("tenant").get("id"), tenant.getId()));
			list = em.createQuery(criteria).getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}
	
	@Override
	public List<Instance> listInstancesBySubOrder(Integer subOrderId) {
		List<Instance> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Instance> criteria = builder.createQuery(Instance.class);
			Root<Instance> root = criteria.from(Instance.class);
			criteria.select(root).where(builder.equal(root.get("subOrder").get("id"), subOrderId));
			list = em.createQuery(criteria).getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return list;
	}

}
