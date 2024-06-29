package com.ktsapi.api.rest;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.enums.ABotActions;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import io.restassured.response.Response;

public class RestDriverImpl implements RestDriver {
	
	private RestContext restContext;
	RequestSpecification requestSpecification = given();
	
	public RestDriverImpl(RestContext restContext) {
		this.restContext = restContext;
		requestSpecification.headers(this.restContext.getHeaders());
	}

	public Response get() {
        return requestSpecification.get(this.restContext.getRequestURL());

	}

	@Override
	public Response post() {
		requestSpecification.body(this.restContext.getBody());
		return requestSpecification.post(this.restContext.getRequestURL());
		
	}
	
	// TODO: update after fix cache log issues
	private void logRequest() {
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GET, "Request URL: "+this.restContext.getRequestURL(), null));
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.GET, "Headers: "+this.restContext.getHeaders(), null));
	}
}
