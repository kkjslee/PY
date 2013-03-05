package com.inforstack.openstack.mail;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.inforstack.openstack.mail.conf.MailConfigation;
import com.inforstack.openstack.mail.template.MailTemplate;

@Entity
public class Mail {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String code;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private MailConfigation sender;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="mail")
	private List<MailTemplate> tempaltes;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
