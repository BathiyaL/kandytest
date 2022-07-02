package com.ktsapi.exceptions;

import org.testng.SkipException;

public class WebDriverProviderException extends SkipException {

	public WebDriverProviderException(String message) {
		super(message);		
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
