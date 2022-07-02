package com.ktsapi.exceptions;

import org.testng.SkipException;

public class TestSuiteValidationException extends SkipException {

	public TestSuiteValidationException(String message) {
		super(message);		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
