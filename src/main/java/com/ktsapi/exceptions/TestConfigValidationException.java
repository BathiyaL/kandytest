package com.ktsapi.exceptions;

import org.testng.SkipException;

public class TestConfigValidationException  extends SkipException {

	public TestConfigValidationException(String message) {
		super(message);		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
