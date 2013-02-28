package com.inforstack.openstack.exception;

public class ApplicationRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 6660035979200279215L;

	public ApplicationRuntimeException(String message){
		super(message);
	}
	
	public ApplicationRuntimeException(Exception e){
		super(e);
	}

}
