package com.inforstack.openstack.instance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class VirtualMachine {

	@Id
	@GeneratedValue
	private int id;
	
	private String uuid;
	
	private String name;
	
	private String image;
	
	private String flavor;
	
	@Column(name="fixed_ip")
	private String fixedIp;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getFixedIp() {
		return fixedIp;
	}

	public void setFixedIp(String fixedIp) {
		this.fixedIp = fixedIp;
	}
	
}
