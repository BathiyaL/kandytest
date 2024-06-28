package com.ktsapi.api.rest;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

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
}
