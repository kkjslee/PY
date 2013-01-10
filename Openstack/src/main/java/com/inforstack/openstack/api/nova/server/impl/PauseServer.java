package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class PauseServer implements ServerAction {

	@JsonProperty("pause")
	private Object pause = null;
	
}
