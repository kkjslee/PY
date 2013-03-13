package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class ConfirmResizeServer implements ServerAction {

	@JsonProperty("confirmResize")
	private Object confirmResize = null;
	
}
