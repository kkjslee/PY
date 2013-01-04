package com.inforstack.openstack.security.role;

import javax.persistence.EntityManager;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDaoImpl implements RoleDao {
	
	private static final Log log = LogFactory.getLog(RoleDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public Role findById(Integer roleId) {
		log.debug("getting Role instance with id: " + roleId);
		if(roleId==null) return null;
		
		try {
			Role instance = em.find(Role.class, roleId);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}	}

}
