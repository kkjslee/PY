package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;

public class UserModel {
	
	private String firstname;
	
	private String lastname;
	
	@Pattern(regexp="^(\\s)*|(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,8}(-\\d{1,4})?$", message="{not.valid}")
	private String phone;
	
	@Pattern(regexp="^(\\s)*|(86)*0*1[3,5,8]\\d{9}$", message="{not.valid}")
	private String mobile;
	
	private String country;
	
	private String province;
	
	private String city;
	
	private String address;
	
	@Pattern(regexp="^(\\s)*|[1-9]\\d{5}(?!\\d)$", message="{not.valid}")
	private String postcode;
	
	private Integer defaultLanguage;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Integer defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}
}
