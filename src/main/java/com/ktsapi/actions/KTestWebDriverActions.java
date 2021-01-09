package com.ktsapi.actions;

public interface KTestWebDriverActions extends WebDriverActions, EnhancedWebElementActions{

	<C> C GetWebPage(Class<C> page);
	
	void Log(String loggerMessage);
	
	
}
