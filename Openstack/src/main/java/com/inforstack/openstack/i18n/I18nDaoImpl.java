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

	@Override
	public void persist(I18n i18n) {
		log.debug("persist i18n with link: " + i18n.getI18nLinkId());
		try{
			em.persist(i18n);
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			throw re;
		}
		
		log.debug("persist successful");
	}

	@Override
	public void remove(I18n i18n) {
		log.debug("Remove i18n : " + i18n.getId());
		try{
			em.remove(i18n);
		}catch(RuntimeException re){
			log.error("Remove failed", re);
			throw re;
		}
		
		log.debug("Remove successful");
	}

	@Override
	public I18n findById(Integer i18nId) {
		log.debug("Find i18b by id : " + i18nId);
		
		I18n i18n = null;
		try{
			i18n = em.find(I18n.class, i18nId);
		}catch(RuntimeException re){
			log.error("Find failed", re);
			throw re;
		}
		
		if(i18n == null){
			log.debug("Find failed");
		}else{
			log.debug("Find successful");
		}
		return i18n;
	}

	@Override
	public List<I18n> findByLinkId(Integer i18nLinkId) {
		log.debug("Find i18ns by link id : " + i18nLinkId);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<I18n> criteria = builder
					.createQuery(I18n.class);
			Root<I18n> root = criteria.from(I18n.class);
			criteria.select(root).where(
					builder.equal(root.get("i18nLinkId"), i18nLinkId)
			);
			List<I18n> instances = em.createQuery(criteria).getResultList();
			if(instances != null && !instances.isEmpty()){
				log.debug("Find successful");
				return instances;
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("Find failed", re);
			throw re;
		}
	}
	
	
}
