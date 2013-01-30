package com.inforstack.openstack.report;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.inforstack.openstack.utils.db.AbstractDao;

@Repository
public class ReportDaoImpl extends AbstractDao<Report> implements ReportDao {

}
