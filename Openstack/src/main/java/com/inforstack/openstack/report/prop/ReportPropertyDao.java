package com.inforstack.openstack.report.prop;

import java.util.List;

import com.inforstack.openstack.utils.db.IDao;

public interface ReportPropertyDao extends IDao<ReportProperty> {

	public List<ReportProperty> findByReport(int reportId);

}
