package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class StopServer implements ServerAction {
	
	@JsonProperty("os-stop")
	private Object stop = null;
	
}
