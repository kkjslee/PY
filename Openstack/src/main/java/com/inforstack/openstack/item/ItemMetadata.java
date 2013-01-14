package com.inforstack.openstack.item;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemMetadata {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String value;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name="item_id")
	private ItemSpecification itemSpecification;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

//	public ItemSpecification getItemSpecification() {
//		return itemSpecification;
//	}
//
//	public void setItemSpecification(ItemSpecification itemSpecification) {
//		this.itemSpecification = itemSpecification;
//	}
	
}
