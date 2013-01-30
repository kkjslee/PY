package com.inforstack.openstack.item;

import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.inforstack.openstack.i18n.link.I18nLink;

@Entity
public class ItemSpecification {
	
	public static final int OS_TYPE_NONE_ID = 0;
	public static final int OS_TYPE_FLAVOR_ID = 1;
	public static final int OS_TYPE_IMAGE_ID = 2;
	public static final int OS_TYPE_VOLUME_ID = 3;
	public static final int OS_TYPE_NETWORK_ID = 4;
	public static final int OS_TYPE_USAGE_ID = 5;
	
	public static final String OS_TYPE_NONE = "";
	public static final String OS_TYPE_FLAVOR = "openstack.flavor";
	public static final String OS_TYPE_IMAGE = "openstack.image";
	public static final String OS_TYPE_VOLUME = "openstack.volume";
	public static final String OS_TYPE_NETWORK = "openstack.network";
	public static final String OS_TYPE_USAGE = "openstack.usage";
	
	@Id
	@GeneratedValue
	private int id;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private I18nLink name;
	
	@Column(name="os_type")
	private int osType;
	
	@Column(name="ref_id")
	private String refId;
	
	private boolean available;
	
	private Date created;
	
	private Date updated;
	
	@Column(name="default_price")
	private float defaultPrice;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemSpecification")
	private List<Price> prices;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "item_category", 
		joinColumns = { @JoinColumn(name = "item_id", insertable=false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "category_id", insertable=false, updatable = false) }
	)
	private List<Category> categories;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemSpecification")
	private List<ItemMetadata> metadata;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=true)
	@JoinColumn
	private Profile profile;

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
	
	public float getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public int getOsType() {
		return osType;
	}

	public void setOsType(int osType) {
		this.osType = osType;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ItemMetadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<ItemMetadata> metadata) {
		this.metadata = metadata;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
