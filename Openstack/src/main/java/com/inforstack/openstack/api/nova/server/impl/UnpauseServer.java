package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class UnpauseServer implements ServerAction {

	@JsonProperty("unpause")
	private Object unpause = null;
	
}
