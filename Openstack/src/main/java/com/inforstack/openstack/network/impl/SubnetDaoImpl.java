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
import com.inforstack.openstack.network.Subnet;
import com.inforstack.openstack.network.SubnetDao;
import com.inforstack.openstack.tenant.Tenant;

@Repository
public class SubnetDaoImpl extends BasicDaoImpl<Subnet> implements SubnetDao {

	@Override
	public List<Subnet> listSubnets(Tenant tenant, Network network, Integer dataCenterId) {
		List<Subnet> list = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Subnet> criteria = builder.createQuery(Subnet.class);
			Root<Subnet> root = criteria.from(Subnet.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (tenant != null) {
				predicates.add(
					builder.equal(root.get("tenant").get("id"), tenant.getId())
				);
			}
			if (network != null) {
				predicates.add(
					builder.equal(root.get("network").get("id"), network.getId())
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
