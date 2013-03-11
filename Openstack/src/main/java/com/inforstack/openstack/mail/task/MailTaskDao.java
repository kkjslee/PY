package com.inforstack.openstack.mail.task;

import com.inforstack.openstack.basic.BasicDao;
import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;

public interface MailTaskDao extends BasicDao<MailTask> {

	CursorResult<MailTask> findAll();

}
