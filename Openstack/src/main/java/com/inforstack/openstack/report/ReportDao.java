package com.inforstack.openstack.report;

public interface ReportDao {

	public void persist(Report report);

	public Report findById(int reportId);

}
