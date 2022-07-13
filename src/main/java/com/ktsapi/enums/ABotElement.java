package com.ktsapi.enums;

public enum ABotElement {
	
	EnhancedWebElement("com.ktsapi.elements.impl.EnhancedWebElementImpl");
	
	private String elementImpl;
	
	private ABotElement(String element) {
		this.elementImpl = element;
	}
	
	public String getElementImpl() {
		return elementImpl;
	}
}
