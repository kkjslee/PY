package com.inforstack.openstack.mail;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.basic.BasicDaoImpl.CursorResult;
import com.inforstack.openstack.exception.ApplicationRuntimeException;
import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.mail.conf.MailConfigation;
import com.inforstack.openstack.mail.conf.MailConfigationService;
import com.inforstack.openstack.mail.task.MailTask;
import com.inforstack.openstack.mail.task.MailTaskService;
import com.inforstack.openstack.mail.template.MailTemplate;
import com.inforstack.openstack.mail.template.MailTemplateService;
import com.inforstack.openstack.utils.Constants;
import com.inforstack.openstack.utils.CryptoUtil;
import com.inforstack.openstack.utils.NumberUtil;
import com.inforstack.openstack.utils.OpenstackUtil;
import com.inforstack.openstack.utils.StringUtil;

@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService {
	
	private static final Logger log = new Logger(MailServiceImpl.class);
	
	@Autowired
	private MailDao mailDao;
	@Autowired
	private MailTemplateService mailTemplateService;
	@Autowired
	private MailConfigationService mailConfigationService;
	@Autowired
	private MailTaskService mailTaskService;
	
	@Override
	public Mail findMailById(int mailId){
		return mailDao.findById(mailId);
	}
	
	@Override
	public Mail findMailByCode(String code){
		return mailDao.findByObject("code", code);
	}
	
	@Override
	public MailTemplate findMailTempalte(int mailId, Integer languageId){
		return mailTemplateService.findTemplateByMailId(mailId, languageId);
	}
	
	@Override
	public Mail updateMailSender(int mailId, MailConfigation sender){
		Mail mail = mailDao.findById(mailId);
		if(mail == null){
			return null;
		}
		
		mail.setSender(sender);
		return mail;
	}
	
	@Override
	public Mail updateMailSender(int mailId, int senderId){
		MailConfigation sender = mailConfigationService.findMailConfigationById(senderId);
		
		if(sender == null){
			return null;
		}
		
		return this.updateMailSender(mailId, sender);
	}
	
	@Override
	public MailTask addMailTask(String mailCode, String toMail, int language, Map<String, Object> propertise, int priority){
		Mail mail = this.findMailByCode(mailCode);
		if(mail == null) {
			log.error("No mail found for mailCode : " + mailCode);
			throw new ApplicationRuntimeException("No mail found");
		}
		
		MailTemplate tempalte = this.findMailTempalte(mail.getId(), language);
		if(tempalte == null){
			tempalte = this.findMailTempalte(mail.getId(), null);
		}
		if(tempalte == null){
			log.error("Cannot find template for mailCode : " + mailCode);
			throw new ApplicationRuntimeException("No template found");
		}
		
		StringBuilder text = new StringBuilder();
		String head = OpenstackUtil.setProperty(tempalte.getHead(), propertise);
		String body = OpenstackUtil.setProperty(tempalte.getBody(), propertise);
		String signature = OpenstackUtil.setProperty(tempalte.getSignature(), propertise);
		if(tempalte.getType() == Constants.MAILTEMPALTE_TYPE_HTML){
			text.append("<p>").append(head).append("</p><p>")
				.append(body).append("</p><br/><p>")
				.append(signature).append("</p>");
		}else{
			text.append(head).append("\n").
				append(body).append("\n\n")
				.append(signature);
		}
		
		return mailTaskService.createMailtask(mail.getSender(), toMail, tempalte.getTitle(), text.toString(), 
				(tempalte.getType() == Constants.MAILTEMPALTE_TYPE_HTML), priority);
	}
	
	@Override
	@Scheduled(fixedDelay=10000)
	public void sendMail(){
		CursorResult<MailTask> mts = mailTaskService.findAll();
		while(mts.hasNext()){
			sendMail(mts.getNext().getId());
		}
		mts.close();
	}
	
	
	public void sendMail(int mailTaskId){
		MailTask mailTask = mailTaskService.findById(mailTaskId);
		if(mailTask == null){
			log.warn("Cannot find mail task by id : " + mailTaskId);
			return;
		}
		MailConfigation sender =mailTask.getSender();
		if(sender == null){
			log.error("Cannot find sender for mailTask : " + mailTask.getId());
			return;
		}
		
		try{
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(sender.getHost());
			mailSender.setProtocol(sender.getProtocal() == null? "smtp" : sender.getProtocal());
			mailSender.setPort(NumberUtil.getInteger(sender.getPort(), 25));
			mailSender.setUsername(sender.getUsername());
			mailSender.setPassword(CryptoUtil.hexXor(sender.getPassword(), sender.getUsername()));
			String properties = sender.getProperties();
			if(StringUtil.isNullOrEmpty(properties) == false){
				Properties props = new Properties();
				props.load(new StringReader(properties));
				mailSender.setJavaMailProperties(props);
			}
			
			MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage());
			helper.setFrom(sender.getUsername());
			helper.setTo(mailTask.getMailTo());
			helper.setSubject(mailTask.getSubject());
			helper.setText(mailTask.getText(), mailTask.getHtml());
			mailSender.send(helper.getMimeMessage());
			
			mailTaskService.removeTask(mailTask);
		}catch(IOException ioe){
			log.error("Error occured while loading mail properties", ioe);
		}catch(MessagingException me){
			log.error("Error occured while building message", me);
		}catch(RuntimeException re){
			log.error("Runtime Exception while sending mail", re);
		}
	}
}
