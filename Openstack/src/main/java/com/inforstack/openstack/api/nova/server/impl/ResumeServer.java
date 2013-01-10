package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class ResumeServer implements ServerAction {

	@JsonProperty("resume")
	private Object resume = null;
	
}
