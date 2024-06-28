package com.ktsapi;

import com.ktsapi.api.KandyTestAPIDriverActions;
import com.ktsapi.api.rest.RestContext;
import com.ktsapi.api.rest.RestDriver;

public class APIActions {

	public static RestDriver getRestDriver(RestContext restContext) {
		return newInstance().getRestDriver(restContext);
	}
	private static KandyTestAPIDriverActions newInstance(){
		return KTestActonsHandler.apiDriverActionsInstance();
	}
}
