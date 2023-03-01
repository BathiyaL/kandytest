package com.ktsapi.enums;

public enum ABotElement {
	
	EnhancedWebElement("com.ktsapi.elements.impl.EnhancedWebElementImpl"),
	EnhancedMobileElement("com.ktsapi.mobile.EnhancedMobileElementImpl");
	
	private String elementImpl;
	
	private ABotElement(String element) {
		this.elementImpl = element;
	}
	
	public String getElementImpl() {
		return elementImpl;
	}
}
