package com.inforstack.openstack.mail.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailConfigationServiceImpl implements MailConfigationService{
	
	@Autowired
	private MailConfigationDao mailConfigationDao;

	@Override
	public MailConfigation findMailConfigationById(int id){
		return mailConfigationDao.findById(id);
	}
	
}
