package com.ktsapi.mobile;

public interface EnhancedMobileElement extends BaseMobileElement{

	public void type(CharSequence... keysToSend);
	//public void click();
	public void scrollToElement();
	
	// parameterize elements
	public void click(String... locatorParams);
	public void typeWithLocatorParms(String textToType, String... locatorParams);
}