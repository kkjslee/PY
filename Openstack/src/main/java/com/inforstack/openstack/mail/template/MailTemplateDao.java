package com.inforstack.openstack.mail.template;

import java.util.List;

import com.inforstack.openstack.basic.BasicDao;

public interface MailTemplateDao extends BasicDao<MailTemplate>{

	List<MailTemplate> findTemplateByMailIdAndLanguage(Integer mailId, Integer languageId);
	
}
