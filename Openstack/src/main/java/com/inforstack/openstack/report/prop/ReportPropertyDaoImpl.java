package com.inforstack.openstack.report.prop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.billing.process.conf.BillingProcessConfiguration;
import com.inforstack.openstack.utils.CollectionUtil;

@Repository
public class ReportPropertyDaoImpl implements ReportPropertyDao {
	
	private static final Log log = LogFactory.getLog(ReportPropertyDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(ReportProperty reportProperty) {
		log.debug("Persist report property");
		try {
			em.persist(reportProperty);
			log.debug("Persist report property successfully");
		} catch (RuntimeException re) {
			log.error("Persist report property failed", re);
			throw re;
		}
		
	}

	@Override
	public List<ReportProperty> findByReport(int reportId) {
		log.debug("Find all report properties by report : " + reportId);
		
		try {
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<ReportProperty> criteria = builder
					.createQuery(ReportProperty.class);
			Root<ReportProperty> root = criteria.from(ReportProperty.class);
			criteria.select(root).where(
					builder.equal(root.get("report.id"), reportId)
			);
			List<ReportProperty> instances = em.createQuery(criteria).getResultList();
			if(CollectionUtil.isNullOrEmpty(instances)){
				log.debug("No record found");
				return null;
			}else{
				log.debug( instances.size() + " record(s) found");
				return instances;
			}
			
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw re;
		}
	}

}
