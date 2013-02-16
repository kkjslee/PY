package com.inforstack.openstack.item;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.inforstack.openstack.i18n.link.I18nLink;

@Entity
public class Category {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	@JoinColumn
	private I18nLink name;
	
	@Column(name="name_id", insertable=false, updatable=false)
	private int nameId;

	private boolean enable;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "item_category", joinColumns = { @JoinColumn(name = "category_id", insertable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "item_id", insertable = false, updatable = false) })
	private List<ItemSpecification> itemSpecifications;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public I18nLink getName() {
		return name;
	}

	public void setName(I18nLink name) {
		this.name = name;
	}

	public int getNameId() {
		return nameId;
	}

	public void setNameId(int nameId) {
		this.nameId = nameId;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<ItemSpecification> getItemSpecifications() {
		return itemSpecifications;
	}

	public void setItemSpecifications(List<ItemSpecification> itemSpecifications) {
		this.itemSpecifications = itemSpecifications;
	}

}
