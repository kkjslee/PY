package com.inforstack.openstack.i18n;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.inforstack.openstack.i18n.link.I18nLink;

@Entity
@Table(name="i18n")
public class I18n {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="language_id")
	private Integer languageId;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="i18n_link_id")
	private I18nLink i18nLink;
	
	@Column(name="i18n_link_id", insertable=false, updatable=false)
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
	public I18nLink getI18nLink() {
		return i18nLink;
	}
	public void setI18nLink(I18nLink i18nLink) {
		this.i18nLink = i18nLink;
	}
}
