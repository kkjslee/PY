package com.inforstack.openstack.controller.model;

import com.inforstack.openstack.utils.OpenstackUtil;

public class ItemSpecificationModel {

	private Integer id;

	private I18nModel[] name;

	private float defaultPrice;

	private Integer osType;

	private String refId;

	private Boolean available;

	private String created;

	private String updated;

	private CategoryModel[] categories;

	private ItemMetadataModel[] metadata;

	private ProfileModel profile;

	public ItemSpecificationModel() {

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

	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public Integer getOsType() {
		return osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public CategoryModel[] getCategories() {
		return categories;
	}

	public void setCategories(CategoryModel[] categories) {
		this.categories = categories;
	}

	public ItemMetadataModel[] getMetadata() {
		return metadata;
	}

	public void setMetadata(ItemMetadataModel[] metadata) {
		this.metadata = metadata;
	}

	public ProfileModel getProfile() {
		return profile;
	}

	public void setProfile(ProfileModel profile) {
		this.profile = profile;
	}

	public String getEnabledDesc() {
		if (available == true) {
			return OpenstackUtil.getMessage("product.status.enabled");
		} else {
			return OpenstackUtil.getMessage("product.status.disabled");
		}
	}

}
