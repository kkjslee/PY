package com.inforstack.openstack.controller.model;


public class AttachmentModel {

	private String id;
	
	private String server;
	
	private String volume;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
}
