package com.katf.element.test;

import static com.ktsapi.CommonActions.pause;
import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.FindElement;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.SwitchTo;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;


@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		//browserVersion="74",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"},
		executionMode = ExecutionMode.GRID
)
public class SwitchToTest {
	
	@BeforeTest
	public void beforeTest1() {
	      print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in beforeTest1");
	      OpenBrowser();
	      //indElement(By.name("iframe2_enterText2")).type("Hellow World 1");
	      //String chromeOptions[] = {"--start-maximized","--ignore-certificate-errors"};
	 }
	
    @Test
    public void testMethod(){

    	// TODOD : complete naviationImple class 
    	//OpenBrowser();
    	System.out.println("++++++++++ :: " + Thread.currentThread().getId());
    	GoTo("http://www.softwareautomationengineer.com/demo-site");
    	FindElement(By.linkText("Web Elements Page 1")).click();
    	
    	SwitchTo().frame("iframe-1");
    	pause(10);    	
    	SwitchTo().frame(0);
    	FindElement(By.name("iframe2_enterText")).type("Hellow World 1");
    	SwitchTo().parentFrame();
    	SwitchTo().frame(0);
    	FindElement(By.name("iframe2_enterText")).type("Hellow World 2");
    	
    	SwitchTo().defaultContent();
    	FindElement(By.name("enterText")).type("Hellow World 0");
    	
    	//pause(5);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>TestCache.getTestClassName() :: " + TestInitializr.getTestClassName());
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>COMPLETED SwitchToTest");

    }
	




}
