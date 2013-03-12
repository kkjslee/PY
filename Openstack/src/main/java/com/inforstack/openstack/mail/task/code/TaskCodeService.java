package com.inforstack.openstack.mail.task.code;

import java.util.Map;

public interface TaskCodeService {

	TaskCode findTaskCode(String mailCode, String random);

	TaskCode createTaskCode(String mailCode, String entityId,
			Map<String, String> properties);

	void removeTaskCode(TaskCode tc);

}
