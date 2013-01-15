package com.inforstack.openstack.tenant;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TenantDaoImpl implements TenantDao{
	
	private static final Log log = LogFactory.getLog(TenantDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(Tenant tenant) {
		log.debug("persist tenant :" + tenant.getName());
		try{
			em.persist(tenant);
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			throw re;
		}
		log.debug("persist successfully");
	}

	@Override
	public Tenant findById(Integer tenantId) {
		log.debug("find tenant by id " + tenantId);
		Tenant instance = null;
		try {
			instance = em.find(Tenant.class, tenantId);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
		
		if(instance == null){
			log.debug("get failed");
		}else{
			log.debug("get successful");
		}
		
		return instance;
	}

}
