package com.ktsapi;

import com.ktsapi.api.KandyTestAPIDriverActions;
import com.ktsapi.api.rest.RestContext;
import com.ktsapi.api.rest.RestDriver;

public class APIActions {

	private static KandyTestAPIDriverActions newInstance(){
		return KTestActonsHandler.apiDriverActionsInstance();
	}
	
	public static RestDriver getRestDriver(RestContext restContext) {
		return newInstance().getRestDriver(restContext);
	}
	public static RestContext getRestContext() {
		return newInstance().getRestContext();
	}
}
