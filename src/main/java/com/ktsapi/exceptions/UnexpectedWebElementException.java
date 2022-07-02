package com.ktsapi.exceptions;

public class UnexpectedWebElementException extends RuntimeException{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnexpectedWebElementException(String expectedTagName, String actualTagName) {
		    super(String.format(
		        "Element tag name should have been \"%s\" but was \"%s\"", expectedTagName, actualTagName));
		  }

}
