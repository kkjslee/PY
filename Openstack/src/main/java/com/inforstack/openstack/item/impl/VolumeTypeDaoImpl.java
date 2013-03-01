package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.VolumeType;
import com.inforstack.openstack.item.VolumeTypeDao;

@Repository
public class VolumeTypeDaoImpl extends BasicDaoImpl<VolumeType> implements VolumeTypeDao {

	@Override
	public String getVolumeRefId(int dataCenterId, String uuid) {
		String refId = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<VolumeType> criteria = builder.createQuery(VolumeType.class);
			Root<VolumeType> root = criteria.from(VolumeType.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("dataCenter").get("id"), dataCenterId));
			predicates.add(builder.equal(root.get("uuid"), uuid));
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<VolumeType> volumeTypes = em.createQuery(criteria).getResultList();
			if (volumeTypes != null && volumeTypes.size() > 0) {
				refId = volumeTypes.get(0).getRefId();
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return refId;
	}

}
