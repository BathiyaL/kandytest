package com.ktsapi.actions;

/** 
 * @author bladduwahetty
 * ABot defined actions goes here
 */
public interface KandyTestWebDriverActions extends WebDriverActions, EnhancedWebElementActions{
	
	<C> C GetWebPage(Class<C> page);
	
	void Log(String loggerMessage);

}
