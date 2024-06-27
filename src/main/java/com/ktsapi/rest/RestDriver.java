package com.ktsapi.rest;

import io.restassured.response.Response;

public interface RestDriver {

	public Response get();
	public Response post();
}
