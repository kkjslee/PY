package com.inforstack.openstack.item.impl;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.item.Profile;
import com.inforstack.openstack.item.ProfileDao;

@Repository
public class ProfileDaoImpl implements ProfileDao {

	private static final Log log = LogFactory.getLog(ProfileDaoImpl.class);
	
	@Autowired
	private EntityManager em;
	
	@Override
	public Profile findById(int id) {
		Profile instance = null;
		log.debug("getting Profile instance with id: " + id);
		try {
			instance = em.find(Profile.class, id);
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
		}
		if (instance == null) {
			log.debug("get failed");
		} else {
			log.debug("get successful");
		}
		return instance;
	}

	@Override
	public Profile persist(Profile profile) {
		try {
			em.persist(profile);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
			return null;
		}
		return profile;
	}

	@Override
	public void update(Profile profile) {
		try {
			em.merge(profile);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(Profile profile) {
		try {
			em.remove(profile);
		} catch (RuntimeException re){
			log.error(re.getMessage(), re);
		}
	}

	@Override
	public void remove(int id) {
		Profile instance = null;
		log.debug("removing Profile instance with id: " + id);
		try {
			instance = em.find(Profile.class, id);
			if (instance != null) {
				em.remove(instance);
				log.debug("remove successful");
			} else {
				log.debug("remove failed");
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
		}
	}

}
