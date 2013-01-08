package com.inforstack.openstack.api.nova.server;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Addresses {
	
	@JsonProperty("private")
	private Address[] privateList;
	
	@JsonProperty("public")
	private Address[] publicList;

	public Address[] getPrivateList() {
		return privateList;
	}

	public void setPrivateList(Address[] privateList) {
		this.privateList = privateList;
	}

	public Address[] getPublicList() {
		return publicList;
	}

	public void setPublicList(Address[] publicList) {
		this.publicList = publicList;
	}

}
