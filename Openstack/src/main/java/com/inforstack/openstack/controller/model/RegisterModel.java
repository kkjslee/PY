package com.inforstack.openstack.controller.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class RegisterModel {
	
	@Size(min=6, max=45, message="{size.not.valid}")
	@Pattern(regexp="^[0-9a-zA-Z_]+$", message="{not.valid}")
	private String username;
	
	@Size(min=6, max=45, message="{size.not.valid}")
	private String password;

	@Email(message="{not.valid}")
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
