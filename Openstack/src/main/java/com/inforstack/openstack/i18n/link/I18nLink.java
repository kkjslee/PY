package com.inforstack.openstack.i18n.link;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import com.inforstack.openstack.i18n.I18n;
import com.inforstack.openstack.i18n.I18nService;
import com.inforstack.openstack.utils.OpenstackUtil;

@Entity
@Table(name = "i18n_link")
public class I18nLink {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "column_name")
	private String columnName;

	@Column(name = "create_time")
	private Date createTime;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "i18nLink")
	private List<I18n> i18ns = new ArrayList<I18n>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<I18n> getI18ns() {
		return i18ns;
	}

	public void setI18ns(List<I18n> i18ns) {
		this.i18ns = i18ns;
	}

	@Transient
	@Transactional
	public String getI18nContent() {
		I18nService i18nService = (I18nService) OpenstackUtil
				.getBean("i18nService");
		return i18nService.getI18nContent(this);
	}
}
