package com.inforstack.openstack.mail;

import com.inforstack.openstack.mail.conf.MailConfigation;
import com.inforstack.openstack.mail.template.MailTemplate;

public interface MailService {

	Mail findMailById(int mailId);

	MailTemplate findMailTempalte(int mailId, int languageId);

	Mail updateMailSender(int mailId, MailConfigation sender);

	Mail updateMailSender(int mailId, int senderId);

	Mail findMailByCode(String code);

}
