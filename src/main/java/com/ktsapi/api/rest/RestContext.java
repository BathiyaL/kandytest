package com.ktsapi.api.rest;

import java.util.Map;
import com.ktsapi.core.TestInitializr;

public class RestContext {
	
	private String baseURI;
	private String endPoint;
	private Map<String, ?> headers;
	private String body;
	private String bearerToken;

	public RestContext() {
		this.baseURI = TestInitializr.getTestConfiguration().getBaseUrl();
	}
	
	public String getBearerToken() {
		return bearerToken;
	}

	public void setBearerToken(String bearerToken) {
		this.bearerToken = "Bearer" + bearerToken;
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
	
	public Map<String, ?> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, ?> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
