package com.inforstack.openstack.report;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDaoImpl implements ReportDao {

	private static final Log log = LogFactory.getLog(ReportDaoImpl.class);
	@Autowired
	private EntityManager em;
	
	@Override
	public void persist(Report report) {
		log.debug("Persist report");
		try {
			em.persist(report);
			log.debug("Persist report successfully");
		} catch (RuntimeException re) {
			log.error("Persist report failed", re);
			throw re;
		}
	}

	@Override
	public Report findById(int reportId) {
		log.debug("Find report by id : " + reportId);
		
		Report report = null;
		try {
			report = em.find(Report.class, reportId);
		} catch (RuntimeException re) {
			log.error("Find report failed", re);
			throw re;
		}
		
		if(report == null){
			log.debug("No instance found");
		}else{
			log.debug("Find report successfully");
		}
		
		return report;
	}
	
}
