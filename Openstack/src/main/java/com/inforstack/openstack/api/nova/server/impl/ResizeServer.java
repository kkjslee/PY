package com.inforstack.openstack.api.nova.server.impl;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inforstack.openstack.api.nova.server.ServerAction;

public class ResizeServer implements ServerAction {
	
	private static final class Resize {

		@JsonProperty("flavorRef")
		private String flavorRef;
		
	}
	
	@JsonProperty("resize")
	private Resize resize = new Resize();
	
	public ResizeServer(String flavorRef) {
		this.resize.flavorRef = flavorRef;
	}
	
}
