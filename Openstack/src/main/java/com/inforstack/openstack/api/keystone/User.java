package com.inforstack.openstack.api.keystone;

public class User {
	
	private String id;
	
	private String name;
	
	private String username;
	
	private Role[] roles;
	
	private String[] roles_links;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role[] getRoles() {
		return roles;
	}

	public void setRoles(Role[] roles) {
		this.roles = roles;
	}

	public String[] getRoles_links() {
		return roles_links;
	}

	public void setRoles_links(String[] roles_links) {
		this.roles_links = roles_links;
	}

}
