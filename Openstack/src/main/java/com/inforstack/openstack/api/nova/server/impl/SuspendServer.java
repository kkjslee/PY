package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class SuspendServer implements ServerAction {

	@JsonProperty("suspend")
	private Object suspend = null;
	
}
