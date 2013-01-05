package com.inforstack.openstack.configuration;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDaoImpl implements ConfigurationDao {

	private static final Log log = LogFactory.getLog(ConfigurationDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public Configuration findByName(String name) {
		log.debug("getting Configuration instance with name : " + name);
		Configuration configuration = null;
		if (name != null) {
			try {
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Configuration> criteria = builder.createQuery(Configuration.class);
				Root<Configuration> root = criteria.from(Configuration.class);
				criteria.select(root).where(builder.equal(root.get("name"), name));;
				List<Configuration> instances = em.createQuery(criteria).getResultList();
				if (instances != null && instances.size() > 0){
					log.debug("get successful");
					configuration = instances.get(0);
				} else {
					log.debug("No record found for name : " + name);
				}
			} catch (RuntimeException re) {
				log.error("get failed", re);
				throw re;
			}
		}
		return configuration;
	}

}
