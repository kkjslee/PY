package com.inforstack.openstack.mail.template;

import java.util.List;

public interface MailTemplateService {

	MailTemplate findMailTemplateById(int mailTemplateId);

	List<MailTemplate> findTemplatesByMailId(int mailId);

	MailTemplate findTemplateByMailId(int mailId, Integer languageId);

}
