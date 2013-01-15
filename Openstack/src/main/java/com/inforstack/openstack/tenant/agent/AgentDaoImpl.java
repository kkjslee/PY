package com.inforstack.openstack.tenant.agent;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AgentDaoImpl implements AgentDao {
	
	private static final Log log = LogFactory.getLog(AgentDaoImpl.class);
	@Autowired
	private EntityManager em;
	@Override
	public Agent findById(String agentId) {
		log.debug("getting Agent instance with id: " + agentId);
		try {
			Agent instance = em.find(Agent.class, agentId);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
