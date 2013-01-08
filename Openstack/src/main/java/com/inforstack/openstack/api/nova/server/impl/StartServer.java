package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class StartServer implements ServerAction {

	@JsonProperty("os-start")
	private Object start = null;
	
}
