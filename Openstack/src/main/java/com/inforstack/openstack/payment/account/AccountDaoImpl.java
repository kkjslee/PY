package com.inforstack.openstack.payment.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.Constants;

@Repository
public class AccountDaoImpl extends BasicDaoImpl<Account> implements AccountDao {
	
	private static final Logger log = new Logger(AccountDaoImpl.class);

	@Override
	public Account findActiveAccount(Integer tenantId, Integer instanceId) {
		log.debug("find active Account by tenant : " + tenantId + ", instanceId : " + instanceId);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Account> criteria = builder
					.createQuery(Account.class);
			Root<Account> root = criteria.from(Account.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("status"), Constants.ACCOUNT_STATUS_ACTIVE));
			if(tenantId != null){
				predicates.add(builder.equal(root.get("tenant").get("id"), tenantId));
			}
			if(instanceId != null){
				predicates.add(builder.equal(root.get("instance").get("id"), instanceId));
			}
			
			criteria.select(root).where(
					builder.and(
						predicates.toArray(new Predicate[predicates.size()])
					)
			);
			List<Account> instances = em.createQuery(criteria).getResultList();
			if(instances!=null && instances.size()>0){
				log.debug("get successful");
				return instances.get(0);
			}
			log.debug("No record found ");
			return null;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
