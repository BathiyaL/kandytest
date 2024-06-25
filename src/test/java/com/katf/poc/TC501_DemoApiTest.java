package com.katf.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;

@TestConfiguration(testDriver = TestDriver.WEB,
	browser = Browsers.CHROME,
	baseUrl = "http://watir.com/examples/shadow_dom.html",
	browserOptions = { "--start-maximized", "--ignore-certificate-errors" }
)
public class TC501_DemoApiTest {

	@BeforeTest
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/";
    }

    @Test
    public void testGetTestPlanRunBuId() throws JsonMappingException, JsonProcessingException{
        Response response = given().log().all()
				                .pathParam("testPlanRunId",75)
				                .queryParam("workspaceId", 1)
				                .when()
				                .get("testPlanRuns/{testPlanRunId}/")
				                .then().log().all()
				                .statusCode(200)
				                .body("testPlanRunId",equalTo("75"))
				                .extract()
				                
				                .response();
        //response.reques
        System.out.println(response.asPrettyString());
    }
}
