package com.ktsapi.kclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.dto.TestPlanRequest;
import com.ktsapi.dto.TestResultRequest;
import com.ktsapi.utils.AvtomatUtils;

//TODO : Clean the code | need to get urls from config file
public class KandyClientApiCaller {
	
	static final String KANDY_CLIENT_URL = "http://localhost:8080";
	static final String testPlanAutomatedRunEndpoint = "/api/testPlanAutomatedRun";
	static final String testPlanRunEndpoint = "/api/testPlanRun";
	
	public static TestPlanRequest postTestPlanRun(TestPlanRequest testPlanRequest) throws ClientProtocolException, IOException {	
		
		TestPlanRequest responseTestPlan;
		
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();

			String       postUrl       = KANDY_CLIENT_URL + testPlanRunEndpoint;
			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));
			System.out.println(">>> : " + gson.toJson(testPlanRequest));
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);
			
			HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);
	        
	        Gson gson2 = new Gson();
	        responseTestPlan = gson2.fromJson(content, TestPlanRequest.class);
	       	        
	        if(responseTestPlan.getTestPlanRunId()==null) {	        	
	        	ConfigLogger.logWarn("@TestSuite{ [KandyClient] Test Plan Run Initiate Error : "+ content +" }");
	        }
	        
	        return responseTestPlan;
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ [KandyClient] :: Test Plan Run Initiate Error : "+ ex.getMessage() +" }");
		}
        
        return null;	
	}
	
	public static TestPlanRequest postTestPlanAutomatedRun(TestPlanRequest testPlanRequest) throws ClientProtocolException, IOException {		
		
		TestPlanRequest responseTestPlan;
		
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();

			String       postUrl       = KANDY_CLIENT_URL + testPlanAutomatedRunEndpoint;
			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));
			
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);
			
			HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);
	        
	        Gson gson2 = new Gson();
	        responseTestPlan = gson2.fromJson(content, TestPlanRequest.class);
	       	        
	        if(responseTestPlan.getTestPlanRunId()==null) {	        	
	        	ConfigLogger.logWarn("@TestSuite{ [KandyClient] Test Plan Automated Run Initiate Error : "+ content +" }");
	        }
	        
	        return responseTestPlan;
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ [KandyClient] :: Test Plan Automated Run Initiate Error : "+ ex.getMessage() +" }");
		}
        
        return null;		
	}
	
	public static void postTestResult(TestResultRequest testResultRequest) throws ClientProtocolException, IOException {
		
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();

			String       postUrl       = KANDY_CLIENT_URL + "/api/testPlanRun/"+ testResultRequest.getTestPlanId() + "/testCaseRunResult";			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testResultRequest));
			
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);		

			HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);

	        if(response.getStatusLine().getStatusCode() == 201) {
	        	ConfigLogger.logInfo("@TestSuite{ [KandyClient] => Post test resutls of " + testResultRequest.getTestCaseId() +" }");
	        } else{ 
	        	ConfigLogger.logError("@TestSuite{ [KandyClient] :: Test result post error for test - > " + testResultRequest.getTestCaseId() + " | Status Code : " +  response.getStatusLine().getStatusCode() + " | Response : " +  content  +" }");
	        }
	        
	        
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ [KandyClient] :: Test Plan Initiate Error : "+ ex.getMessage() +" }");
		}		
	}
	
	public static void updateTestPlanAsCompleted(String testPlanID) throws ClientProtocolException, IOException {
		
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();

			String postUrl = KANDY_CLIENT_URL + testPlanAutomatedRunEndpoint;		
			TestPlanRequest testPlanRequest = new TestPlanRequest();
			testPlanRequest.setTestPlanAutomatedRunId(testPlanID);		
			testPlanRequest.setTestPlanRunStatus("Completed");
			testPlanRequest.setExecutionCompletedTimestamp(AvtomatUtils.localDateTimeStringFormat(AvtomatUtils.getCurretnTimeStamp()));
			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));
					
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);
			
			HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);
	        
	        if(response.getStatusLine().getStatusCode() == 201) {
	        	 ConfigLogger.logInfo("@TestSuite{ [KandyClient] => Updated automated testplan as completed ");
	        } else {
	        	 ConfigLogger.logInfo("@TestSuite{ [KandyClient] => Automated testplan update error -> " + content);
	        }
	       
			
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ [KandyClient] => Test Plan Initiate Error : "+ ex.getMessage() +" }");
		}	
	
		
	}

}
