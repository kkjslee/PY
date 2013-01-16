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

}
