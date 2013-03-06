package com.inforstack.openstack.network.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.network.Network;
import com.inforstack.openstack.network.NetworkDao;
import com.inforstack.openstack.tenant.Tenant;

@Repository
public class NetworkDaoImpl extends BasicDaoImpl<Network> implements NetworkDao {

	@Override
	public List<Network> listNetworks(Tenant tenant, Integer dataCenterId) {
		List<Network> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Network> criteria = builder.createQuery(Network.class);
			Root<Network> root = criteria.from(Network.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (tenant != null) {
				predicates.add(
					builder.equal(root.get("tenant").get("id"), tenant.getId())
				);
			}
			if (dataCenterId != null) {
				predicates.add(
					builder.equal(root.get("dataCenter").get("id"), dataCenterId)
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
