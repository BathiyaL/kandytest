package com.ktsapi.exceptions;

public class InvalidLocatorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidLocatorException(String errorMessage) {
	    super(errorMessage);
	}

}
