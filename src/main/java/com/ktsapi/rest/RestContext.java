package com.ktsapi.rest;

import com.ktsapi.core.TestInitializr;

public class RestContext {
	
	private String baseURI;
	private String endPoint;
	
	public RestContext() {
		this.baseURI = TestInitializr.getTestConfiguration().getBaseUrl();
	}
	
	public RestContext(String baseURI) {
		this.baseURI = baseURI;
	}
	
	public String getBaseURI() {
		return baseURI;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	public String getRequestURL() {
		// TODO: need url validation
		return baseURI + endPoint;
	}

}
