package com.inforstack.openstack.api;

public class OpenstackAPIException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4910087284240719832L;
	
	public OpenstackAPIException(String message, Exception e) {
		super(message, e);
	}

	public OpenstackAPIException(String message) {
		super(message);
	}

}
