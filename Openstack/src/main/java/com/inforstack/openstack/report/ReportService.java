package com.inforstack.openstack.report;

import java.util.Map;

public interface ReportService {
	
	/**
	 * create report and report properties
	 * @param report
	 * @param propertyMap
	 * @return
	 */
	public Report createReport(Report report, Map<String, String> propertyMap);
	
	/**
	 * create report and report properties
	 * @param type
	 * @param status
	 * @param propertyMap
	 * @return
	 */
	public Report createReport(int type, int status, Map<String, String> propertyMap);
	
	/**
	 * find report
	 * @param reportId
	 * @return
	 */
	public Report findReportById(int reportId);
	
}
