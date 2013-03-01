package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.Image;
import com.inforstack.openstack.item.ImageDao;

@Repository
public class ImageDaoImpl extends BasicDaoImpl<Image> implements ImageDao {

	@Override
	public String getImageRefId(int dataCenterId, String uuid) {
		String refId = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Image> criteria = builder.createQuery(Image.class);
			Root<Image> root = criteria.from(Image.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("dataCenter").get("id"), dataCenterId));
			predicates.add(builder.equal(root.get("uuid"), uuid));
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			List<Image> images = em.createQuery(criteria).getResultList();
			if (images != null && images.size() > 0) {
				refId = images.get(0).getRefId();
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return refId;
	}

}
