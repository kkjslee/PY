package com.inforstack.openstack.report.prop;

import java.util.List;

import com.inforstack.openstack.report.Report;

public interface ReportPropertyService {
	
	/**
	 * create report property
	 * @param reportProperty
	 * @return
	 */
	public ReportProperty createReportProperty(ReportProperty reportProperty);
	
	/**
	 * create report property
	 * @param report
	 * @param name
	 * @param value
	 * @return
	 */
	public ReportProperty createReportProperty(Report report, String name, String value);
	
	/**
	 * find report properties by report id
	 * @param reportId
	 * @return
	 */
	public List<ReportProperty> findReportProperties(int reportId);
}
