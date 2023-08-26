package com.ktsapi.exceptions;

import org.testng.SkipException;

public class WebDriverNotFoundException extends SkipException {

	public WebDriverNotFoundException(String message) {
		super(message);		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
