package com.ktsapi.actions.core;

import com.ktsapi.core.TestInitializr;

public interface DriverTimeOuts {
	long DEFAULT_IMPLICITLY_WAIT_TIME = 5;
	long DEFAULT_PAGELOAD_TIMEOUT = 45;
	long DEFAULT_SCRIPT_TIMEOUT = 20;
	
	public static long getImplicitlyWaitTime(){		
		if(null!=TestInitializr.getImplicitlyWaitTime()) {
			return TestInitializr.getImplicitlyWaitTime();
		}
		return DEFAULT_IMPLICITLY_WAIT_TIME;
	};
	
	public static long getScriptTimeout(){
		if(null!=TestInitializr.getScriptTimeout()) {
			return TestInitializr.getImplicitlyWaitTime();
		}
		return DEFAULT_SCRIPT_TIMEOUT;
	};
	
	public static long getPageLoadTimeout(){
		if(null!=TestInitializr.getPageLoadTimeout()) {
			return TestInitializr.getImplicitlyWaitTime();
		}
		return DEFAULT_PAGELOAD_TIMEOUT;
	};
	
	void setImplicitlyWaitTime(long implicitlyWaitTimeInSeconds);
	void setScriptTimeout(long scriptTimeoutInSeconds);
	void setPageLoadTimeout(long pageLoadTimeoutInSeconds);
}
