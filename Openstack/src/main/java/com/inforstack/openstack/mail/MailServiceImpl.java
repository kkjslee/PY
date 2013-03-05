package com.inforstack.openstack.mail;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.log.Logger;
import com.inforstack.openstack.mail.conf.MailConfigation;
import com.inforstack.openstack.mail.conf.MailConfigationService;
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
	
	@Override
	public Mail findMailById(int mailId){
		return mailDao.findById(mailId);
	}
	
	@Override
	public Mail findMailByCode(String code){
		return mailDao.findByObject("code", code);
	}
	
	@Override
	public MailTemplate findMailTempalte(int mailId, int languageId){
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
	
	@Async
	public void sendMail(String mailCode, String toMail, int language, Map<String, Object> propertise){
		MailService self = (MailService)OpenstackUtil.getBean("mailService");
		Mail mail = self.findMailByCode(mailCode);
		if(mail == null) {
			log.warn("No mail found for mailCode : " + mailCode);
			return;
		}
		
		MailConfigation sender = mail.getSender();
		MailTemplate tempalte = self.findMailTempalte(mail.getId(), language);
		if(sender == null || tempalte == null){
			log.warn("Cannot find sender or template for mailCode : " + mailCode);
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
			helper.setTo(toMail);
			helper.setSubject(tempalte.getTitle());
			String head = OpenstackUtil.setProperty(tempalte.getHead(), propertise);
			String body = OpenstackUtil.setProperty(tempalte.getBody(), propertise);
			String signature = OpenstackUtil.setProperty(tempalte.getSignature(), propertise);
			if(tempalte.getType() == Constants.MAILTEMPALTE_TYPE_HTML){
				helper.setText("<p>"+ head + "</p><p>" +body + "</p><br/><p>" + signature + "</p>", true);
			}else{
				helper.setText(head + "\n" + body + "\n\n" + signature);
			}
			mailSender.send(helper.getMimeMessage());
		}catch(IOException ioe){
			log.error("Error occured while loading mail properties", ioe);
		}catch(MessagingException me){
			log.error("Error occured while building message", me);
		}catch(RuntimeException re){
			log.error("Runtime Exception while sending mail", re);
		}
	}
}
