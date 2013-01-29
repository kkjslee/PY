package com.inforstack.openstack.report.prop;

import java.util.List;

import com.inforstack.openstack.report.Report;

public interface ReportPropertyService {
	
	public ReportProperty createReportProperty(ReportProperty reportProperty);
	
	public ReportProperty createReportProperty(Report report, String name, String value);
	
	public List<ReportProperty> findReportProperties(int reportId);
}
