package com.inforstack.openstack.controller.model;


public class CategoryModel {
	
	private Integer id;
	
	private I18nModel[] name;
	
	private Boolean enable;
	
	public CategoryModel() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public I18nModel[] getName() {
		return name;
	}

	public void setName(I18nModel[] name) {
		this.name = name;
	}

	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
