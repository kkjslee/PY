package com.inforstack.openstack.report.prop;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface ReportPropertyDao extends BasicDao<ReportProperty> {

	public List<ReportProperty> findByReport(int reportId);

}
