package com.inforstack.openstack.item;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private int id;

	private String name;
	
	private boolean enable;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "item_category", 
		joinColumns = { @JoinColumn(name = "item_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "category_id", insertable=false, updatable = false) }
	)
	private List<ItemSpecification> itemSpecifications;
	
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
