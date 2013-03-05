package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.NetworkType;
import com.inforstack.openstack.item.NetworkTypeDao;

@Repository
public class NetworkTypeDaoImpl extends BasicDaoImpl<NetworkType> implements NetworkTypeDao {

	@Override
	public boolean isWebSite(int dataCenterId, String uuid) {
		boolean ret = false;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<NetworkType> criteria = builder.createQuery(NetworkType.class);
			Root<NetworkType> root = criteria.from(NetworkType.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("dataCenter").get("id"), dataCenterId));
			predicates.add(builder.equal(root.get("uuid"), uuid));
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<NetworkType> networkTypes = em.createQuery(criteria).getResultList();
			if (networkTypes != null && networkTypes.size() > 0) {
				ret = networkTypes.get(0).isWeb();
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return ret;
	}	
	
}
