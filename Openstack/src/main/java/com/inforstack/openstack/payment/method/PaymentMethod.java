package com.inforstack.openstack.payment.method;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.inforstack.openstack.i18n.link.I18nLink;
import com.inforstack.openstack.payment.method.prop.PaymentMethodProperty;

@Entity
@Table(name="payment_method")
public class PaymentMethod {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer type;
	
	private Integer catlog;
	
	private String icon;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="text")
	private I18nLink text;
	
	private String endpoint;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="method")
	private List<PaymentMethodProperty> propertise;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public I18nLink getText() {
		return text;
	}

	public void setText(I18nLink text) {
		this.text = text;
	}

	public List<PaymentMethodProperty> getPropertise() {
		return propertise;
	}

	public void setPropertise(List<PaymentMethodProperty> propertise) {
		this.propertise = propertise;
	}

	public Integer getCatlog() {
		return catlog;
	}

	public void setCatlog(Integer catlog) {
		this.catlog = catlog;
	}
	
}
