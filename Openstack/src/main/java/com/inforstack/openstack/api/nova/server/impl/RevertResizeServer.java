package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class RevertResizeServer implements ServerAction {

	@JsonProperty("revertResize")
	private Object revertResize = null;
	
}
