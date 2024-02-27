package com.katf.tests;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.GetTitle;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.getWebPage;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.FBPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
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
public class TC2_TestPass {
	
	@BeforeTest
	public void goSite() {	 	 
	      OpenBrowser();
	      
	      GoTo(TestInitializr.getTestConfiguration().getBaseUrl());
	      print(GetTitle());
	 }
	
	@BeforeTest(dependsOnMethods = {"goSite"})
	public void login() {	      
		FBPage fbPage = getWebPage(FBPage.class);
		fbPage.username.type("invalid_username");
		fbPage.password.type("invalid_password");
		fbPage.submit.click();		
	 }
	
	@Test
	public void testMothod() {		
		print("Test Pass");
	}

}