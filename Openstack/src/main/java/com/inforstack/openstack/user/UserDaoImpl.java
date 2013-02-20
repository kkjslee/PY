package com.inforstack.openstack.user;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;

@Repository
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

	private static final Logger log = new Logger(UserDaoImpl.class);

	@Override
	public User findByName(String userName) {
		log.debug("getting User instance with name : " + userName);

		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> user = criteria.from(User.class);
			criteria.select(user).where(
					builder.equal(user.get("username"), userName));
			;
			List<User> instances = em.createQuery(criteria).getResultList();
			if (instances != null && instances.size() > 0) {
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
	public User findByNameAndEmail(String userName, String email) {
		log.debug("getting User instance with name: " + userName
				+ "and email: " + email);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			Root<User> user = criteria.from(User.class);
			criteria.select(user).where(
					builder.and(builder.equal(user.get("username"), userName),
							builder.equal(user.get("email"), email)));
			List<User> instances = em.createQuery(criteria).getResultList();
			if (instances != null && instances.size() > 0) {
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found for name: " + userName + "and email: "
					+ email);
			return null;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
