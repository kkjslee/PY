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
	public Tenant persist(Tenant tenant) {
		log.debug("persist tenant :" + tenant.getName());
		try{
			em.persist(tenant);
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		log.debug("persist successfully");
		return tenant;
	}

}
