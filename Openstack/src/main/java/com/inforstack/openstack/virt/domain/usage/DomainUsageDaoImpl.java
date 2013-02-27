package com.inforstack.openstack.virt.domain.usage;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;
import com.inforstack.openstack.log.Logger;

@Repository
public class DomainUsageDaoImpl extends BasicDaoImpl<DomainUsage> implements DomainUsageDao{
	
	private static final Logger log = new Logger(DomainUsageDaoImpl.class);

	@Override
	public DomainUsage find(Integer instanceId, String instanceUuid,
			Date logTime) {
		DomainUsage intance = null;
		log.debug("getting DomainUsage instance with instanceId : " + instanceId + ", instanceUuid : "
				+ instanceUuid + ", logTime : " + logTime);
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<DomainUsage> criteria = builder.createQuery(DomainUsage.class);
			Root<DomainUsage> root = criteria.from(DomainUsage.class);
			criteria.select(root).where(
					builder.and(
						builder.equal(root.get("instanceId"), instanceId),
						builder.equal(root.get("instanceUuid"), instanceUuid),
						builder.equal(root.get("logTime"), logTime)
					)		
			);
			List<DomainUsage> instances = em.createQuery(criteria).getResultList();
			if (instances != null && instances.size() > 0) {
				log.debug("get successful");
				intance = instances.get(0);
			} else {
				log.debug("No record found");
			}
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
		}
		return intance;
	}

}
