package com.inforstack.openstack.item;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.inforstack.openstack.i18n.link.I18nLink;

@Entity
@Table(name="item_metadata")
public class ItemMetadata {

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private I18nLink name;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private I18nLink value;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="item_id")
	private ItemSpecification itemSpecification;

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

	public I18nLink getValue() {
		return value;
	}

	public void setValue(I18nLink value) {
		this.value = value;
	}

	public ItemSpecification getItemSpecification() {
		return itemSpecification;
	}

	public void setItemSpecification(ItemSpecification itemSpecification) {
		this.itemSpecification = itemSpecification;
	}
	
}
