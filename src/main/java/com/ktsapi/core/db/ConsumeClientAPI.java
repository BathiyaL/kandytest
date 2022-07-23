package com.ktsapi.core.db;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ktsapi.dto.Testplan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConsumeClientAPI {

	
	public static void postTestPlan(Testplan testPlan) throws ClientProtocolException, IOException {
		
		Testplan testPlan2 = new Testplan("tep3");
		testPlan2.setTestPlanName("AQR");
		
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();  
		Gson gson = gsonBuilder.create();		
		
		System.out.println("--------- " + gson.toJson(testPlan2));
		
		String postUrl = "http://localhost:8080".concat("/testPlan");
	
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(gson.toJson(testPlan2));
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
		
	}
}
