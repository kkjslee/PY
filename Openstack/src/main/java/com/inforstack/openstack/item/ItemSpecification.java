package com.inforstack.openstack.item;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ItemSpecification {
	
	@Id
	@GeneratedValue
	private int id;
	
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

	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	private String name;
	
	private float defaultPrice;
	
	private String refId;
	
	private List<Category> categories;

}
