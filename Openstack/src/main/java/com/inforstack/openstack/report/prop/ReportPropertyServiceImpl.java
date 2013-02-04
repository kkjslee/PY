package com.inforstack.openstack.report.prop;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.report.Report;
import com.inforstack.openstack.utils.CollectionUtil;

@Service
@Transactional
public class ReportPropertyServiceImpl implements ReportPropertyService {
	
	private static final Logger log = new Logger(ReportPropertyServiceImpl.class);
	@Autowired
	private ReportPropertyDao reportPropertyDao;
	
	@Override
	public ReportProperty createReportProperty(ReportProperty reportProperty) {
		log.debug("Create report property");
		reportPropertyDao.persist(reportProperty);
		log.debug("Create report property successfully");
		
		return reportProperty;
	}
	
	@Override
	public ReportProperty createReportProperty(Report report, String name,
			String value) {
		log.debug("Create report property with report : " + report.getId() + ", name : " + name +
				", value : " + value);
		ReportProperty reportProperty = new ReportProperty();
		reportProperty.setName(name);
		reportProperty.setValue(value);
		reportProperty.setReport(report);
		reportPropertyDao.persist(reportProperty);
		log.debug("Create report property successfully");
		
		return reportProperty;
	}

	@Override
	public List<ReportProperty> findReportProperties(int reportId) {
		log.debug("Find all report properties : " + reportId);
		List<ReportProperty> rps = reportPropertyDao.findByReport(reportId);
		if(CollectionUtil.isNullOrEmpty(rps)){
			log.debug("No instance found");
			return new ArrayList<ReportProperty>();
		}else{
			log.debug(rps.size() + " records found");
			return rps;
		}
	}
	
	
}
