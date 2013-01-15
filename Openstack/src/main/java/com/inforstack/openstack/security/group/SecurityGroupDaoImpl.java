package com.inforstack.openstack.security.group;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityGroupDaoImpl implements SecurityGroupDao {
	
	private static final Log log = LogFactory.getLog(SecurityGroupDaoImpl.class);
	
	@Autowired
	private EntityManager em;

}
