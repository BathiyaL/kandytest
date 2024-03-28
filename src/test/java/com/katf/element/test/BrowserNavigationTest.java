package com.katf.element.test;

import static com.ktsapi.CommonActions.pause;
import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage1;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		//browserVersion="74",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"}
)

public class BrowserNavigationTest {


    @Test
    public void testMethod(){

    	// TODOD : complete naviationImple class 
    	openBrowser();
    	goTo("http://www.softwareautomationengineer.com/demo-site");
    	findElement(By.linkText("Web Elements Page 1")).click();
    	pause(1);
    	browserNavigate().back();
    	pause(1);
    	browserNavigate().forward();
    	pause(1);
    	browserNavigate().to("https://www.google.com/");   	
 
    	try {
    		URL myURL = new URL("http://www.softwareautomationengineer.com/demo-site");
			browserNavigate().to(myURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	pause(2);
    	
    	browserNavigate().refresh();
    	pause(2);

    }
	


}
