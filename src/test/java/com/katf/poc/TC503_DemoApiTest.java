package com.katf.poc;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.api.rest.RestContext;
import com.ktsapi.api.rest.RestDriver;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.APIActions;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

@TestConfiguration(
		testDriver = TestDriver.API,
		baseUrl = "https://petstore.swagger.io"
)
public class TC503_DemoApiTest {

	RestContext getRestContext;
	RestContext postRestContext;
	RestDriver restDriver;
	long petId;
	
	@BeforeTest
    public void setup(){
		postRestContext = APIActions.getRestContext();
		postRestContext.setEndPoint("/v2/pet");
		postRestContext.setBody(getPetPayload());
		
		getRestContext = postRestContext;
		postRestContext.setEndPoint("/v2/pet");	
    }

    @Test(priority=0)
    public void testPostRequest(){
    	restDriver = APIActions.getRestDriver(postRestContext);
        Response response = restDriver.post();
        petId = response.jsonPath().getLong("id");
		System.out.println(response.asPrettyString());
    }
    
    
    // TODO : Currently TestListner is not support multiple tests in a single class.
    @Test(priority=1)
    public void testGetRequest(){
    	getRestContext.setEndPoint("/v2/pet/"+petId);
    	restDriver = APIActions.getRestDriver(getRestContext);
    	Response response = restDriver.get();
		System.out.println(response.asPrettyString());
    }
    
    private String getPetPayload() {
    	return   "{\"id\":0,\"category\":{\"id\":0,\"name\":\"Dogs\"},\"name\":\"Rover\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
    }
}
