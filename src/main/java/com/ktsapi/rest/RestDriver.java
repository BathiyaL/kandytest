package com.ktsapi.rest;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class RestDriver {
	
	private RestContext restContext;
	
	public RestDriver(RestContext restContext) {
		this.restContext = restContext;
	}

	public Response get() {
		RequestSpecification requestSpecification = given();
        return requestSpecification.get(this.restContext.getRequestURL());

	}
}
