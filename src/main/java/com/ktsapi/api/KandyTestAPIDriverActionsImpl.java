package com.ktsapi.api;

import java.util.HashMap;

import com.ktsapi.api.rest.RestContext;
import com.ktsapi.api.rest.RestDriver;
import com.ktsapi.api.rest.RestDriverImpl;

public class KandyTestAPIDriverActionsImpl implements KandyTestAPIDriverActions{

	@Override
	public RestDriver getRestDriver(RestContext restContext) {
		return new RestDriverImpl(restContext);
	}

	@Override
	public RestContext getRestContext() {
		RestContext restContext = new RestContext();
		restContext.setHeaders(getDefaultHeader());
		return restContext;
	}
	
	private HashMap<String, String> getDefaultHeader(){
		HashMap<String, String> defaultHeaders = new HashMap<>();
		defaultHeaders.put("Content-Type", "application/json");
		defaultHeaders.put("Accept", "application/json");
		return defaultHeaders;
	}

}
