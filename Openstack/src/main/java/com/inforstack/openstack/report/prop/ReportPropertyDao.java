package com.inforstack.openstack.report.prop;

import java.util.List;

public interface ReportPropertyDao {

	public void persist(ReportProperty reportProperty);

	public List<ReportProperty> findByReport(int reportId);

}
