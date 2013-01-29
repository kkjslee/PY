package com.inforstack.openstack.report;

import java.util.Map;

public interface ReportService {
	
	public Report createReport(Report report, Map<String, String> propertyMap);
	
	public Report createReport(int type, int status, Map<String, String> propertyMap);
	
	public Report findReportById(int reportId);
	
}
