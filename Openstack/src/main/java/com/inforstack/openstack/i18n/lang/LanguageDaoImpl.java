package com.inforstack.openstack.i18n.lang;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.user.User;


@Repository
public class LanguageDaoImpl implements LanguageDao{
	
	private static final Log log = LogFactory.getLog(LanguageDaoImpl.class);
	@Autowired
	private EntityManager em;
	@Override
	public Language findById(Integer languageId) {
		log.debug("getting Language instance with id: " + languageId);
		if(languageId==null) return null;
		
		try {
			Language instance = em.find(Language.class, languageId);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}	
		
	}
	
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
}
