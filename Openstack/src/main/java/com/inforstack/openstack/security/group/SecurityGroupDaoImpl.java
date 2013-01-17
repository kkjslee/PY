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

	@Override
	public void persist(SecurityGroup securityGroup) {
		log.debug("Persist security group : " + securityGroup.getName());
		try{
			em.persist(securityGroup);
			log.debug("Persist successfully");
		}catch(RuntimeException re){
			log.error("Persist failed");
			throw re;
		}
		
	}

	@Override
	public void merge(SecurityGroup securityGroup) {
		log.debug("Merge security group : " + securityGroup.getName());
		try{
			em.persist(securityGroup);
			log.debug("Merge successfully");
		}catch(RuntimeException re){
			log.error("Merge failed");
			throw re;
		}
	}

	@Override
	public SecurityGroup findById(Integer securityGroupId) {
		log.debug("Find security group by id : " + securityGroupId);
		
		SecurityGroup instance = null;
		try{
			instance = em.find(SecurityGroup.class, securityGroupId);
		}catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
		
		if(instance == null){
			log.debug("Find failed");
		}else{
			log.debug("Find successfully");
		}
		return instance;
	}

	@Override
	public void remove(SecurityGroup securityGroup) {
		log.debug("Remove security group : " + securityGroup.getName());
		try{
			em.remove(securityGroup);
			log.debug("Remove successfully");
		}catch(RuntimeException re){
			log.error("Remove failed");
			throw re;
		}
	}
}
