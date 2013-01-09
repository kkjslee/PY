package com.inforstack.openstack.controller.model;

import com.inforstack.openstack.tenant.Tenant;

public class TenantModel {
	
	private String name;
	
	private String tenantDisplayName;
	
	private String tenantPhone;
	
	private String tenantEmail;
	
	private String tenantCountry;
	
	private String tenantProvince;
	
	private String tenantCity;
	
	private String tenantAddress;
	
	private String tenantPostcode;
	
	public Tenant getTenant(){
		Tenant tenant = new Tenant();
		tenant.setName(name);
		tenant.setDipalyName(tenantDisplayName);
		tenant.setPhone(tenantPhone);
		tenant.setEmail(tenantEmail);
		tenant.setCountry(tenantCountry);
		tenant.setProvince(tenantProvince);
		tenant.setCity(tenantCity);
		tenant.setAddress(tenantAddress);
		tenant.setPostcode(tenantPostcode);
		return tenant;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
