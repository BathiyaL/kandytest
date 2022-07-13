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
	
	public static TestPlanRequest postTestPlan(TestPlanRequest testPlanRequest) throws ClientProtocolException, IOException {		
		
		TestPlanRequest responseTestPlan;
		
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();
			
			//String       postUrl       = KANDY_CLIENT_URL + "/api/testPlanRun";// put in your url
			String       postUrl       = KANDY_CLIENT_URL + "/api/testPlanAutomatedRun";// put in your url
			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));//gson.tojson() converts your pojo to json
			//System.out.println("=============1 :: " + gson.toJson(testPlanRequest));
			
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);
			
			HttpEntity entity = response.getEntity();
	        String content = EntityUtils.toString(entity);
	        
	        Gson gson2 = new Gson();
	        responseTestPlan = gson2.fromJson(content, TestPlanRequest.class);
	        //System.out.println("============= :: " + response.getStatusLine().getStatusCode());
	        
	        if(responseTestPlan.getTestPlanRunId()==null) {
	        	//ConfigLogger.logWarn("@TestSuite{ KandyClient TEP creation response code :  "+ response.getStatusLine() +" }");
	        	ConfigLogger.logWarn("@TestSuite{ KandyClient Test Plan Initiate Error : "+ content +" }");
	        }
	        
	        return responseTestPlan;
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ KandyClient Test Plan Initiate Error : "+ ex.getMessage() +" }");
		}
        
        return null;		
	}
	
	public static void postTestResult(TestResultRequest testResultRequest) throws ClientProtocolException, IOException {
		
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();  
			gsonBuilder.serializeNulls();  
			Gson gson = gsonBuilder.create();
			
			//String       postUrl       = KANDY_CLIENT_URL + "/api/testResult";
			String       postUrl       = KANDY_CLIENT_URL + "/api/testPlanRun/20/testCaseRunResult"; // TODO: need to parameterize the testplan run id
			
			HttpClient   httpClient    = HttpClientBuilder.create().build();
			HttpPost     post          = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity(gson.toJson(testResultRequest));//gson.tojson() converts your pojo to json
			System.out.println("=============1 :: " + gson.toJson(testResultRequest));
			
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse  response = httpClient.execute(post);		
			
			///////////////////////////
//			HttpEntity entity = response.getEntity();
//	        String content = EntityUtils.toString(entity);
//	        
//	        Gson gson2 = new Gson();
//	        TestResultRequest responseTestPlan = gson2.fromJson(content, TestResultRequest.class);
	        System.out.println("============= :: " + response.getStatusLine().getStatusCode());
	        
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ KandyClient Test Plan Initiate Error : "+ ex.getMessage() +" }");
		}		
	}
	
	public static void updateTestPlanAsCompleted(String testPlanID) throws ClientProtocolException, IOException {
		
		try {
//			GsonBuilder gsonBuilder = new GsonBuilder();  
//			gsonBuilder.serializeNulls();  
//			Gson gson = gsonBuilder.create();
//			
//			String       postUrl       = KANDY_CLIENT_URL + "/api/testPlan/" + testPlanID;
//			
//			TestPlanRequest testPlanRequest = new TestPlanRequest();
//			testPlanRequest.setModifiedTimestamp(AvtomatUtils.localDateTimeStringFormat(AvtomatUtils.getCurretnTimeStamp())); // completion timestamp
//			testPlanRequest.setTestPlanStatus("Completed");
//			
//			HttpClient   httpClient    = HttpClientBuilder.create().build();
//			HttpPost     post          = new HttpPost(postUrl);
//			StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));//gson.tojson() converts your pojo to json
//			post.setEntity(postingString);
//			post.setHeader("Content-type", "application/json");
//			HttpResponse  response = httpClient.execute(post);	
		}catch (Exception ex) {
			ConfigLogger.logWarn("@TestSuite{ KandyClient Test Plan Initiate Error : "+ ex.getMessage() +" }");
		}	
	
		
	}

}
