package com.katf.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.rest.RestContext;
import com.ktsapi.rest.RestDriver;
import com.ktsapi.rest.RestDriverImpl;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;

@TestConfiguration(testDriver = TestDriver.API,
	baseUrl = "https://petstore.swagger.io"
)
public class TC503_DemoApiTest {

	RestContext getRestContext;
	RestContext postRestContext;
	RestDriver restDriver;
	
	@BeforeTest
    public void setup(){
		
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		getRestContext = new RestContext();
		//restContext.setEndPoint("/api/testPlanRuns/75/?workspaceId=1");
		getRestContext.setEndPoint("/v2/pet");
		getRestContext.setHeaders(headers);
		getRestContext.setBody(getPetPayload());
		
		postRestContext = getRestContext;
		
		postRestContext.setEndPoint("/v2/pet");
		
		
    }

    @Test
    public void testPostRequest(){
    	restDriver = new RestDriverImpl(postRestContext);
        Response response = restDriver.post();
		System.out.println(response.asPrettyString());
    }
    public void testGetRequest(){
//    	restDriver = new RestDriverImpl(postRestContext);
//    	Response response = restDriver.get();
//		System.out.println(response.asPrettyString());
    }
    
    private String getPetPayload() {
    	return   "{\"id\":0,\"category\":{\"id\":0,\"name\":\"Dogs\"},\"name\":\"Rover\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
    }
}
