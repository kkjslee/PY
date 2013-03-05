package com.inforstack.openstack.mail.template;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.utils.CollectionUtil;

@Service
@Transactional
public class MailTemplateServiceImpl implements MailTemplateService{
	
	@Autowired
	private MailTemplateDao mailTemplateDao;
	
	@Override
	public MailTemplate findMailTemplateById(int mailTemplateId){
		return mailTemplateDao.findById(mailTemplateId);
	}
	
	@Override
	public List<MailTemplate> findTemplatesByMailId(int mailId){
		return mailTemplateDao.findTemplateByMailIdAndLanguage(mailId, null);
	}
	
	@Override
	public MailTemplate findTemplateByMailId(int mailId, int languageId){
		List<MailTemplate> tempaltes = mailTemplateDao.findTemplateByMailIdAndLanguage(mailId, languageId);
		if(CollectionUtil.isNullOrEmpty(tempaltes)){
			return null;
		}
		
		return tempaltes.get(0);
	}

}
