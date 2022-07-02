package com.ktsapi.exceptions;

import org.testng.SkipException;

public class ConfigFileNotFoundException extends SkipException {
	
	public ConfigFileNotFoundException(String message) {
		super(message);		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
