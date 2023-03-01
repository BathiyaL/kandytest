package com.ktsapi.mobile;

public interface EnhancedMobileElement extends BaseMobileElement{

	public void type(CharSequence... keysToSend);
	public void click();
	public void scrollToElement();
	
	// dynamic elements
	public void click(String ... args);
}