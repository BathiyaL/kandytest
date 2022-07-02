package com.ktsapi.exceptions;

import org.testng.SkipException;

public class TestClassNotFoundException extends SkipException {

	public TestClassNotFoundException(String skipMessage) {
		super(skipMessage);		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406435252637419019L;

}
