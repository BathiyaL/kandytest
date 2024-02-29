package com.ktsapi;

import com.ktsapi.actions.CommonDriverAction;

public class CommonActions {

	public static CommonDriverAction newInstance(){
		return KTestActonsHandler.commanDriverActionsInstance();
	}
	
	/** 
	 * @param pause time in seconds
	 *
	 */
	public static void pause(int timeOutInSeconds) {
		try {
			newInstance().pause(timeOutInSeconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // if this cause unexpected result remove 			
		} 
	}
	
	public static void print(String logMessage) {
		newInstance().print(logMessage);
	}
	
	public static String baseUrl() {
		return newInstance().baseUrl();
	}
	
}
