package com.inforstack.openstack.i18n.lang;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;

@Repository
public class LanguageDaoImpl extends BasicDaoImpl<Language> implements LanguageDao{
	
	private static final Log log = LogFactory.getLog(LanguageDaoImpl.class);
	
	@Override
	public Language find(String country, String language) {
		log.debug("find Language instance by country : " + country + ", language : " + language);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder
					.createQuery(Language.class);
			Root<Language> root = criteria.from(Language.class);
			
			criteria.select(root).where(
					builder.and(
						builder.equal(root.get("country"), country),
						builder.equal(root.get("language"), language)
					)
			);
			List<Language> instances = em.createQuery(criteria).getResultList();
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
	public Language find(String language) {
		log.debug("find Language instance by language : " + language);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder
					.createQuery(Language.class);
			Root<Language> root = criteria.from(Language.class);
			criteria.select(root).where(
					builder.equal(root.get("language"), language)
			);
			List<Language> instances = em.createQuery(criteria).getResultList();
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
	public Language getDefault() {
		log.debug("getting Default Language instance");
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Language> criteria = builder
					.createQuery(Language.class);
			Root<Language> root = criteria.from(Language.class);
			criteria.select(root);
			List<Language> instances = em.createQuery(criteria).getResultList();
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
