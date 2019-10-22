package com.etisalat.ops.excemption;

public class UserException extends Exception{	
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public UserException() {
		
	}
	
	public UserException(String message) {
		super(message);
	}
	
	public UserException(String message,Exception e) {
		super(message,e);
	}

	@Override
	public String getMessage() {		
		return this.message;
	}
	
	
	
}
