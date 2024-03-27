package com.katf.tests.clientsamples;

import static com.ktsapi.CommonActions.print;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		baseUrl = "https://www.facebook.com/",
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class TC1_TestFail {
	
	@BeforeTest
	public void goSite() {	 	 
	      print("BeforeTest executed ........");	      
	 }
	
	@BeforeTest(dependsOnMethods = {"goSite"})
	public void login() {	      
		print("BeforeTest login ........");		
	 }
	
	@Test
	public void testMethod() {		
		print("testMethod started ........");	
		Assert.assertEquals(true,false,"TC1 Fail");
	}

}
