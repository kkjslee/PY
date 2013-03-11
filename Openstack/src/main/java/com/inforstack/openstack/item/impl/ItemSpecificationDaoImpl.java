package com.inforstack.openstack.item.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.item.ItemSpecification;
import com.inforstack.openstack.item.ItemSpecificationDao;

@Repository
public class ItemSpecificationDaoImpl extends BasicDaoImpl<ItemSpecification> implements ItemSpecificationDao {

	@Override
	public ItemSpecification findByName(String name) {
		return this.findByObject("name", name);
	}

	@Override
	public ItemSpecification findByTypeAndRefId(int osType, String refId) {
		ItemSpecification item = null;
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ItemSpecification> criteria = builder.createQuery(ItemSpecification.class);
			Root<ItemSpecification> root = criteria.from(ItemSpecification.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("osType"), osType));
			predicates.add(builder.equal(root.get("refId"), refId));
			criteria.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			item = em.createQuery(criteria).getSingleResult();
		} catch (RuntimeException re) {
			log.error("get failed", re);
		}
		return item;
	}

}
