package com.inforstack.openstack.mail.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.mail.conf.MailConfigation;

@Service
@Transactional
public class MailTaskServiceImpl implements MailTaskService{
	
	@Autowired
	private MailTaskDao mailTaskDao;
	
	@Override
	public MailTask createMailtask(MailConfigation sender, String mailTo, String subject,
			String text, boolean html, int priority) {
		MailTask mt = new MailTask();
		mt.setCreateTime(new Date());
		mt.setMailTo(mailTo);
		mt.setSender(sender);
		mt.setSubject(subject);
		mt.setText(text);
		mt.setPriority(priority);
		mt.setHtml(html);
		mt.setDeleted(false);
		
		mailTaskDao.persist(mt);
		return mt;
	}

	@Override
	public void deleteTask(MailTask mailTask) {
		mailTask.setDeleted(true);
	}

	@Override
	public CursorResult<MailTask> findAll() {
		return mailTaskDao.findAll();
	}

	@Override
	public MailTask findById(int mailTaskId) {
		return mailTaskDao.findById(mailTaskId);
	}

	@Override
	public void removeTask(MailTask mailTask) {
		mailTaskDao.remove(mailTask);
	}
}
