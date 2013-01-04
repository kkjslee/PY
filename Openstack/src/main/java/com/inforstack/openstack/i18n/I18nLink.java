package com.inforstack.openstack.i18n;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;


import com.inforstack.openstack.i18n.lang.LanguageService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Entity
public class I18nLink {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String table;
	private String name;
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Transient
	public String getI18nContent(){
		I18nService i18nService = null;
		I18n i18n = i18nService.findByLinkAndLanguage(this.getId(), OpenstackUtil.getLanguage().getId());
		if(i18n==null){
			i18n = i18nService.getFirstByLink(this.getId());
		}
		
		return i18n==null ? null : i18n.getContent();
	}
}
