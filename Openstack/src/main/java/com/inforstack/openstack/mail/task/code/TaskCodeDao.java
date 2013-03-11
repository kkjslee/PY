package com.inforstack.openstack.mail.task.code;

import com.inforstack.openstack.basic.BasicDao;

public interface TaskCodeDao extends BasicDao<TaskCode> {

	TaskCode findByCodeAndRandom(String code, String random);

}
