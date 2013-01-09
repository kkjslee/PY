package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class RebootServer implements ServerAction {
	
	private static final class Reboot {

		@JsonProperty("type")
		private String type = "SOFT";
		
	}
	
	@JsonProperty("reboot")
	private Reboot reboot = new Reboot();

}
