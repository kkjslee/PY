package com.inforstack.openstack.report;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.basic.BasicDaoImpl;

@Repository
public class ReportDaoImpl extends BasicDaoImpl<Report> implements ReportDao {

}
