package com.ktsapi.api;

import com.ktsapi.api.rest.RestContext;
import com.ktsapi.api.rest.RestDriver;
import com.ktsapi.api.rest.RestDriverImpl;

public class KandyTestAPIDriverActionsImpl implements KandyTestAPIDriverActions{

	@Override
	public RestDriver getRestDriver(RestContext restContext) {
		return new RestDriverImpl(restContext);
	}

}
