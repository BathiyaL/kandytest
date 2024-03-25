package com.ktsapi.actions;

public interface CommonDriverAction {
	
	void pause(int timeOut) throws InterruptedException;
	void print(String message);
	String baseUrl();
	void saveScreenshot(String name);
}
