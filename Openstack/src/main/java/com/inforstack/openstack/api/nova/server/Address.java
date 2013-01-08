package com.inforstack.openstack.api.nova.server;

public class Address {
	
	private int version;
	
	private String addr;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
