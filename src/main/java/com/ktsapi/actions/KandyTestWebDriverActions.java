package com.ktsapi.actions;

/** 
 * @author bladduwahetty
 * ABot defined actions goes here
 */
public interface KandyTestWebDriverActions extends WebDriverActions, EnhancedWebElementActions{
	
	<C> C getWebPage(Class<C> page);
	
	void log(String loggerMessage);

}
