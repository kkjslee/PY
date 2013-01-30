package com.inforstack.openstack.i18n.dict;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;

@Repository
public class DictionaryDaoImpl extends BasicDaoImpl<Dictionary> implements DictionaryDao{
	
	private static final Log log = LogFactory.getLog(DictionaryDaoImpl.class);

	@Override
	public List<Dictionary> findByKeyAndLanguage(String key, Integer languageId) {
		log.debug("Find dictionary(s) by key : " + key + " and language : " + languageId);
		try{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Dictionary> criteria = builder
					.createQuery(Dictionary.class);
			Root<Dictionary> dict = criteria.from(Dictionary.class);
			criteria.select(dict).where(
					builder.and(
							builder.equal(dict.get("key"), key),
							builder.equal(dict.get("languageId"), languageId)
					)
			).orderBy(
					builder.asc(dict.get("id"))
			);
			List<Dictionary> instances = em.createQuery(criteria).getResultList();
			if(instances != null && !instances.isEmpty()){
				log.debug("get successful");
				return instances;
			}
			log.debug("No record found");
			return null;
		}catch(RuntimeException re){
			log.error("Find failed", re);
			throw re;
		}
	}

	@Override
	public Dictionary findDictionary(String key, Integer languageId, String code) {
		log.debug("Find dictionary by key : " + key + ", language : " + languageId + ", code : " + code);
		try{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Dictionary> criteria = builder
					.createQuery(Dictionary.class);
			Root<Dictionary> dict = criteria.from(Dictionary.class);
			criteria.select(dict).where(
					builder.and(
							builder.equal(dict.get("key"), key),
							builder.equal(dict.get("languageId"), languageId),
							builder.equal(dict.get("code"), code)
					)
			);
			List<Dictionary> instances = em.createQuery(criteria).getResultList();
			if(instances != null && !instances.isEmpty()){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found");
			return null;
		}catch(RuntimeException re){
			log.error("Find failed", re);
			throw re;
		}
	}

	@Override
	public List<Dictionary> findDictionary(String key, String code) {
		log.debug("Find dictionary by key : " + key + ", code : " + code);
		try{
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Dictionary> criteria = builder
					.createQuery(Dictionary.class);
			Root<Dictionary> dict = criteria.from(Dictionary.class);
			criteria.select(dict).where(
					builder.and(
							builder.equal(dict.get("key"), key),
							builder.equal(dict.get("code"), code)
					)
			);
			List<Dictionary> instances = em.createQuery(criteria).getResultList();
			if(instances != null && !instances.isEmpty()){
				log.debug("get successful");
				return instances;
			}
			log.debug("No record found");
			return null;
		}catch(RuntimeException re){
			log.error("Find failed", re);
			throw re;
		}
	}

}
