package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class HardRebootServer implements ServerAction {
	
	private static final class Reboot {

		@JsonProperty("type")
		private String type = "HARD";
		
	}
	
	@JsonProperty("reboot")
	private Reboot reboot = new Reboot();

}
