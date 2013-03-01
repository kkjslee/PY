package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.Flavor;
import com.inforstack.openstack.item.FlavorDao;

@Repository
public class FlavorDaoImpl extends BasicDaoImpl<Flavor> implements FlavorDao {

	@Override
	public String getFlavorRefId(int dataCenterId, String uuid) {
		String refId = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Flavor> criteria = builder.createQuery(Flavor.class);
			Root<Flavor> root = criteria.from(Flavor.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (dataCenterId != 0) {
				predicates.add(builder.equal(root.get("dataCenter").get("id"), dataCenterId));
			}
			predicates.add(builder.equal(root.get("uuid"), uuid));
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<Flavor> flavors = em.createQuery(criteria).getResultList();
			if (flavors != null && flavors.size() > 0) {
				refId = flavors.get(0).getRefId();
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return refId;
	}

}
