package com.inforstack.openstack.mail;

import java.util.Map;

import com.inforstack.openstack.mail.conf.MailConfigation;
import com.inforstack.openstack.mail.task.MailTask;
import com.inforstack.openstack.mail.template.MailTemplate;

public interface MailService {

	Mail findMailById(int mailId);

	MailTemplate findMailTempalte(int mailId, Integer languageId);

	Mail updateMailSender(int mailId, MailConfigation sender);

	Mail updateMailSender(int mailId, int senderId);

	Mail findMailByCode(String code);

	MailTask addMailTask(String mailCode, String toMail, int language,
			Map<String, Object> propertise, int priority);

	void sendMail();

}
