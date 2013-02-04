package com.inforstack.openstack.report;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.report.prop.ReportPropertyService;
import com.inforstack.openstack.utils.MapUtil;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
	
	private static final Logger log = new Logger(ReportServiceImpl.class);
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private ReportPropertyService reportPropertyService;
	
	@Override
	public Report createReport(Report report, Map<String, String> propertyMap) {
		log.debug("Create report");
		reportDao.persist(report);
		if(!MapUtil.isNullOrEmpty(propertyMap)){
			for(Map.Entry<String, String> entry : propertyMap.entrySet()){
				reportPropertyService.createReportProperty(report, entry.getKey(), entry.getValue());
			}
		}
		
		log.debug("Create report successfully");
		
		return report;
	}

	@Override
	public Report createReport(int type, int status, Map<String, String> propertyMap) {
		log.debug("Create report");
		Report report = new Report();
		report.setCreateTime(new Date());
		report.setStatus(status);
		report.setType(type);
		reportDao.persist(report);
		
		if(!MapUtil.isNullOrEmpty(propertyMap)){
			for(Map.Entry<String, String> entry : propertyMap.entrySet()){
				reportPropertyService.createReportProperty(report, entry.getKey(), entry.getValue());
			}
		}
		log.debug("Create report successfully");
		
		return report;
	}

	@Override
	public Report findReportById(int reportId) {
		log.debug("Find report by id : " + reportId);
		Report report = reportDao.findById(reportId);
		if(report == null){
			log.debug("Find report failed");
		}else{
			log.debug("Find report successfully");
		}
		
		return report;
	}
}
