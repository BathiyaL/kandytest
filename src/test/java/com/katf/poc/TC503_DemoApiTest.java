package com.katf.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.rest.RestContext;
import com.ktsapi.rest.RestDriver;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

@TestConfiguration(testDriver = TestDriver.API,
	baseUrl = "http://localhost:8080"
)
public class TC503_DemoApiTest {

	RestContext restContext;
	RestDriver restDriver;
	
	@BeforeTest
    public void setup(){
		restContext = new RestContext();
		restContext.setEndPoint("/api/testPlanRuns/75/?workspaceId=1");
		restDriver = new RestDriver(restContext);
    }

    @Test
    public void testGetTestPlanRunBuId() throws JsonMappingException, JsonProcessingException{
        Response response = restDriver.get();
		System.out.println(response.asPrettyString());
    }
}
