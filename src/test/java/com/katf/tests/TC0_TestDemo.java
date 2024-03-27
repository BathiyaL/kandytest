package com.katf.tests;

import static com.ktsapi.CommonActions.*;
import static com.ktsapi.WebActons.GetTitle;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
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
public class TC0_TestDemo {
	
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
		OpenBrowser();
		GoTo(baseUrl());
		//FindElement(By.xpath("//lol")).click();
		print("TITLE : " + GetTitle());	
	}

}

