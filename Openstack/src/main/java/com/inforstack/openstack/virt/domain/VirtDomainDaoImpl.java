package com.inforstack.openstack.virt.domain;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.utils.CollectionUtil;
import com.inforstack.openstack.utils.Constants;

@Repository
public class VirtDomainDaoImpl extends BasicDaoImpl<VirtDomain> implements VirtDomainDao {
	
	private static final Logger log = new Logger(VirtDomainDaoImpl.class);

	@Override
	public List<VirtDomain> findVirtDomainsByTenant(int tenantId) {
		log.debug("Find virt domain by tenant : " + tenantId);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<VirtDomain> criteria = builder
					.createQuery(VirtDomain.class);
			Root<VirtDomain> root = criteria.from(VirtDomain.class);
			criteria.select(root).where(
					builder.and(
							builder.notEqual(root.get("status"), Constants.VIRTDOMAIN_STATUS_DELETED),
							builder.equal(root.get("tenant.id"), tenantId)
					)
			);
			List<VirtDomain> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No instance found");
			}else{
				log.debug("Find successfully");
			}
			return instances;
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}
}
