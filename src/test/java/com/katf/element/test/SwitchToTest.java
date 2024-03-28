package com.katf.element.test;

import static com.ktsapi.CommonActions.pause;
import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.findElement;
import static com.ktsapi.WebActons.goTo;
import static com.ktsapi.WebActons.openBrowser;
import static com.ktsapi.WebActons.switchTo;

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
	      openBrowser();
	      //indElement(By.name("iframe2_enterText2")).type("Hellow World 1");
	      //String chromeOptions[] = {"--start-maximized","--ignore-certificate-errors"};
	 }
	
    @Test
    public void testMethod(){

    	// TODOD : complete naviationImple class 
    	//OpenBrowser();
    	System.out.println("++++++++++ :: " + Thread.currentThread().getId());
    	goTo("http://www.softwareautomationengineer.com/demo-site");
    	findElement(By.linkText("Web Elements Page 1")).click();
    	
    	switchTo().frame("iframe-1");
    	pause(10);    	
    	switchTo().frame(0);
    	findElement(By.name("iframe2_enterText")).type("Hellow World 1");
    	switchTo().parentFrame();
    	switchTo().frame(0);
    	findElement(By.name("iframe2_enterText")).type("Hellow World 2");
    	
    	switchTo().defaultContent();
    	findElement(By.name("enterText")).type("Hellow World 0");
    	
    	//pause(5);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>TestCache.getTestClassName() :: " + TestInitializr.getTestClassName());
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>COMPLETED SwitchToTest");

    }
	




}
