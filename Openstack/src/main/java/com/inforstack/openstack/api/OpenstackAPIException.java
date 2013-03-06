package com.inforstack.openstack.api;

public class OpenstackAPIException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4910087284240719832L;
	
	private int code = 0;
	
	public OpenstackAPIException(int code, String message, Exception e) {
		super(message, e);
		this.code = code;
	}
	
	public OpenstackAPIException(String message, Exception e) {
		super(message, e);
	}

	public OpenstackAPIException(String message) {
		super(message);
	}
	
	public int getCode() {
		return this.code;
	}

}
