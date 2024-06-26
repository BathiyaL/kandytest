package com.katf.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.TestDriver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;

@TestConfiguration(testDriver = TestDriver.API
)
public class TC502_DemoApiTest {

	@BeforeTest
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8080/api/testPlanRuns/75/?workspaceId=1";
//        RestAssured.port = 8080;
//        RestAssured.basePath = "/api/";
    }

    @Test
    public void testGetTestPlanRunBuId() throws JsonMappingException, JsonProcessingException{
        Response response = given().log().all()
                .when()
                .get("http://localhost:8080/api/testPlanRuns/75/?workspaceId=1")
                .then().log().all()
                .statusCode(200)
                .body("testPlanRunId",equalTo("75"))
                .extract()
                
                .response();
//response.reques
System.out.println(response.asPrettyString());
    }
}
