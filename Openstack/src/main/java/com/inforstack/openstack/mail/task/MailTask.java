package com.inforstack.openstack.mail.task;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inforstack.openstack.mail.conf.MailConfigation;

@Entity
@Table(name="mail_task")
public class MailTask {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="sender")
	private MailConfigation sender;
	
	@Column(name="mail_to")
	private String mailTo;
	
	private String subject;
	
	private String text;
	
	private boolean html;
	
	private int priority;
	
	private boolean deleted;
	
	@Column(name="create_time")
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MailConfigation getSender() {
		return sender;
	}

	public void setSender(MailConfigation sender) {
		this.sender = sender;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean getHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
}
