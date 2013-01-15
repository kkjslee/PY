package com.inforstack.openstack.user;

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
public class UserDaoImpl implements UserDao {
	
	private static final Log log = LogFactory.getLog(UserDaoImpl.class);
	@Autowired
	private EntityManager em;

	@Override
	public User findUser(Integer userId) {
		log.debug("getting User instance with id: " + userId);
		
		User instance = null;
		try {
			instance = em.find(User.class, userId);
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

	@Override
	public User findByName(String userName) {
		log.debug("getting User instance with name : " + userName);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder
					.createQuery(User.class);
			Root<User> user = criteria.from(User.class);
			criteria.select(user).where(builder.equal(user.get("name"), userName));;
			List<User> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found for name : " + userName);
			return null;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

	@Override
	public void persist(User user) {
		log.debug("persist user : " + user.getName());
		try{
			em.persist(user);
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			throw re;
		}
		
		log.debug("persist successful");
	}

	@Override
	public User merge(User user) {
		log.debug("merge user : " + user.getName());
		try{
			User u = em.merge(user);
			log.debug("merge sucessfully");
			return u;
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			throw re;
		}
		
	}

	@Override
	public void remove(User user) {
		log.debug("remove user : " + user.getName());
		try{
			em.remove(user);
			log.debug("merge successful");
		}catch(RuntimeException re){
			log.error(re.getMessage(), re);
			throw re;
		}
	}


}
