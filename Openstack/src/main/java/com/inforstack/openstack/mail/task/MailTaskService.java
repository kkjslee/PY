package com.inforstack.openstack.mail.task;

import com.inforstack.openstack.mail.conf.MailConfigation;

public interface MailTaskService {

	MailTask createMailtask(MailConfigation sender, String mailTo,
			String subject, String text, boolean html, int priority);

	void deleteTask(MailTask mailTask);
	
}
