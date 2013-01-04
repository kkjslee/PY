package com.inforstack.openstack.i18n;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class I18n {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="language_id")
	private Integer languageId;
	
	@Column(name="i18nlink_id")
	private Integer i18nLinkId;
	
	private String content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	public Integer getI18nLinkId() {
		return i18nLinkId;
	}
	public void setI18nLinkId(Integer i18nLinkId) {
		this.i18nLinkId = i18nLinkId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
