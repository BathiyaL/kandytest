package com.ktsapi.exceptions;
import org.testng.SkipException;
public class FailedToSetupTestDriverException extends SkipException {

	public FailedToSetupTestDriverException(String message) {
		super(message);		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4877578732500521013L;

}
