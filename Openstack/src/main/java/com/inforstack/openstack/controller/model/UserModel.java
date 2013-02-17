package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.inforstack.openstack.user.User;
import com.inforstack.openstack.utils.StringUtil;

public class UserModel {
	
	@Size(min=6, max=45, message="{size.not.valid}")
	@Pattern(regexp="^[0-9a-zA-Z_]+$", message="{not.valid}")
	private String username;
	
	@Size(min=6, max=45, message="{size.not.valid}")
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	@Email(message="{not.valid}")
	private String email;
	
	@Size(min=6, message="{size.min.not.valid}")
	private String question;
	
	@Size(min=4, message="{size.min.not.valid}")
	private String answer;
	
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
	
	public User getUser(){
		User user = new User();
		user.setUsername(StringUtil.trimString(username));
		user.setPassword(StringUtil.trimString(password));
		user.setFirstname(StringUtil.trimString(firstname));
		user.setLastname(StringUtil.trimString(lastname));
		user.setEmail(StringUtil.trimString(email));
		user.setQuestion(StringUtil.trimString(question));
		user.setAnswer(StringUtil.trimString(answer));
		user.setPhone(StringUtil.trimString(phone));
		user.setMobile(StringUtil.trimString(mobile));
		user.setCountry(StringUtil.trimString(country));
		user.setProvince(StringUtil.trimString(country));
		user.setCity(StringUtil.trimString(city));
		user.setAddress(StringUtil.trimString(address));
		user.setPostcode(StringUtil.trimString(postcode));
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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
	
}
