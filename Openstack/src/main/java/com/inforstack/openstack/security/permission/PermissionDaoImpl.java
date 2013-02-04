package com.inforstack.openstack.security.permission;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.user.User;

@Repository
public class PermissionDaoImpl extends BasicDaoImpl<Permission> implements PermissionDao {
	
	private static final Logger log = new Logger(PermissionDaoImpl.class);
	
	@Override
	public List<Permission> findByKey(String key) {
		log.debug("Find Permission instances with key : " + key);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Permission> criteria = builder
					.createQuery(Permission.class);
			Root<Permission> root = criteria.from(Permission.class);
			CriteriaQuery<Permission> query = criteria.select(root);
			if(key!=null && key.length()>0){
				String s = key.replaceAll("\\*", "%");
				query.where(builder.like(root.<String>get("name"), s));
			}
			List<Permission> instances = em.createQuery(criteria).getResultList();
			if(instances==null){
				instances = new ArrayList<Permission>();
			}
			
			if(instances.isEmpty()){
				log.debug("No record found for key : " + key);
			}else{
				log.debug("get successful");
			}
			
			return instances;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
