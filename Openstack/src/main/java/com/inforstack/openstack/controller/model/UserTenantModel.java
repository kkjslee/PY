package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.inforstack.openstack.tenant.Tenant;
import com.inforstack.openstack.utils.StringUtil;

public class UserTenantModel {
	
	private String tenantDisplayName;
	
	@Pattern(regexp="^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,8}(-\\d{1,4})?$", message="{not.valid}")
	private String tenantPhone;
	
	@Email(message="{not.valid}")
	private String tenantEmail;
	
	private String tenantCountry;
	
	private String tenantProvince;
	
	private String tenantCity;
	
	private String tenantAddress;
	
	@Pattern(regexp="^[1-9]\\d{5}(?!\\d)$", message="{not.valid}")
	private String tenantPostcode;
	
	public Tenant getTenant(){
		Tenant tenant = new Tenant();
		tenant.setDipalyName(StringUtil.trimString(tenantDisplayName));
		tenant.setPhone(StringUtil.trimString(tenantPhone));
		tenant.setEmail(StringUtil.trimString(tenantEmail));
		tenant.setCountry(StringUtil.trimString(tenantCountry));
		tenant.setProvince(StringUtil.trimString(tenantProvince));
		tenant.setCity(StringUtil.trimString(tenantCity));
		tenant.setAddress(StringUtil.trimString(tenantAddress));
		tenant.setPostcode(StringUtil.trimString(tenantPostcode));
		return tenant;
	}

	public String getTenantDisplayName() {
		return tenantDisplayName;
	}

	public void setTenantDisplayName(String tenantDisplayName) {
		this.tenantDisplayName = tenantDisplayName;
	}

	public String getTenantPhone() {
		return tenantPhone;
	}

	public void setTenantPhone(String tenantPhone) {
		this.tenantPhone = tenantPhone;
	}

	public String getTenantEmail() {
		return tenantEmail;
	}

	public void setTenantEmail(String tenantEmail) {
		this.tenantEmail = tenantEmail;
	}

	public String getTenantCountry() {
		return tenantCountry;
	}

	public void setTenantCountry(String tenantCountry) {
		this.tenantCountry = tenantCountry;
	}

	public String getTenantProvince() {
		return tenantProvince;
	}

	public void setTenantProvince(String tenantProvince) {
		this.tenantProvince = tenantProvince;
	}

	public String getTenantCity() {
		return tenantCity;
	}

	public void setTenantCity(String tenantCity) {
		this.tenantCity = tenantCity;
	}

	public String getTenantAddress() {
		return tenantAddress;
	}

	public void setTenantAddress(String tenantAddress) {
		this.tenantAddress = tenantAddress;
	}

	public String getTenantPostcode() {
		return tenantPostcode;
	}

	public void setTenantPostcode(String tenantPostcode) {
		this.tenantPostcode = tenantPostcode;
	}
	
}
