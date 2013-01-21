package com.inforstack.openstack.controller.model;

public class ItemMetadataModel {

	private Integer id;
	
	private I18nModel[] name;
	
	private I18nModel[] value;

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

	public I18nModel[] getValue() {
		return value;
	}

	public void setValue(I18nModel[] value) {
		this.value = value;
	}
	
}
