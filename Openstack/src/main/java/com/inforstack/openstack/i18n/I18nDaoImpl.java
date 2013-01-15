package com.inforstack.openstack.i18n;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class I18nDaoImpl implements I18nDao{
	
	private static final Log log = LogFactory.getLog(I18nDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public I18n find(Integer linkId, Integer languageId) {
		log.debug("find I18n instance by i18nLinkId : " + linkId + ", languageId : " + languageId);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<I18n> criteria = builder
					.createQuery(I18n.class);
			Root<I18n> root = criteria.from(I18n.class);
			criteria.select(root).where(
					builder.and(
						builder.equal(root.get("i18nLinkId"), linkId),
						builder.equal(root.get("languageId"), languageId)
					)
			);
			List<I18n> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public I18n findFirst(Integer linkId) {
		log.debug("find I18n instance by i18nLinkId : " + linkId );
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<I18n> criteria = builder
					.createQuery(I18n.class);
			Root<I18n> root = criteria.from(I18n.class);
			criteria.select(root).where(
					builder.equal(root.get("i18nLinkId"), linkId)
			).orderBy(
					builder.asc(root.get("languageId")
			));
			List<I18n> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
