package com.ktsapi;

import com.ktsapi.actions.CommonDriverAction;

public class CommonActions {

	public static CommonDriverAction newInstance(){
		return ABotActonsHandler.commanDriverActionsInstance();
	}
	
	/** 
	 * @param pause time in seconds
	 *
	 */
	public static void pause(int timeOutInSeconds) {
		try {
			newInstance().pause(timeOutInSeconds);
		} catch (InterruptedException e) {
			// TODO for now we can ignore exception and proceed the script
			e.printStackTrace();
		} 
	}
	
	public static void print(String logMessage) {
		newInstance().print(logMessage);
	}
	
	public static String baseUrl() {
		return newInstance().baseUrl();
	}
	
}
