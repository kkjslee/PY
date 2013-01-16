package com.inforstack.openstack.i18n.link;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.i18n.I18n;

@Repository
public class I18nLinkDaoImpl implements I18nLinkDao{

	private static final Log log = LogFactory.getLog(I18nLinkDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(I18nLink link) {
		log.debug("Persist i18n link");
		try{
			em.persist(link);
			log.debug("Persist successfully");
		}catch(RuntimeException re){
			log.error("Persist failed");
			throw re;
		}
	}

	@Override
	public I18nLink findById(Integer linkId) {
		log.debug("Find i18n link by id : " + linkId);
		
		I18nLink link = null;
		try{
			link = em.find(I18nLink.class, linkId);
		}catch(RuntimeException re){
			log.error("Find failed", re);
			throw re;
		}
		
		if(link == null){
			log.debug("No instance found");
		}else{
			log.debug("Find successfully");
		}
		return link;
	}

	@Override
	public void remove(I18nLink link) {
		log.debug("Remove i18n link");
		try{
			em.remove(link);
			log.debug("Remove successfully");
		}catch(RuntimeException re){
			log.error("Remove failed");
			throw re;
		}
	}

	@Override
	public I18nLink findByTableAndColumnName(String tableName, String columnName) {
		log.debug("find I18n link by tableName : " + tableName + ", columnName : " + columnName);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<I18nLink> criteria = builder
					.createQuery(I18nLink.class);
			Root<I18nLink> root = criteria.from(I18nLink.class);
			criteria.select(root).where(
					builder.and(
						builder.equal(root.get("tableName"), tableName),
						builder.equal(root.get("columnName"), columnName)
					)
			);
			List<I18nLink> instances = em.createQuery(criteria).getResultList();
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
