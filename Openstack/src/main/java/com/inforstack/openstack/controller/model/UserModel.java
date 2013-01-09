package com.inforstack.openstack.controller.model;

import com.inforstack.openstack.user.User;

public class UserModel {
	
	private String name;
	
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	private String userEmail;
	
	private String question;
	
	private String answer;
	
	private String userPhone;
	
	private String userMobile;
	
	private String userCountry;
	
	private String userProvince;
	
	private String userCity;
	
	private String userAddress;
	
	private String userPostcode;
	
	public User getUser(){
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(userEmail);
		user.setQuestion(question);
		user.setAnswer(answer);
		user.setPhone(userPhone);
		user.setMobile(userMobile);
		user.setCountry(userCountry);
		user.setProvince(userProvince);
		user.setCity(userCity);
		user.setAddress(userAddress);
		user.setPostcode(userPostcode);
		return user;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPostcode() {
		return userPostcode;
	}

	public void setUserPostcode(String userPostcode) {
		this.userPostcode = userPostcode;
	}
	
}
