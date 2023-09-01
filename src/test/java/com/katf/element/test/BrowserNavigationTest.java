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
    	OpenBrowser();
    	GoTo("http://www.softwareautomationengineer.com/demo-site");
    	FindElement(By.linkText("Web Elements Page 1")).click();
    	pause(1);
    	BrowserNavigate().back();
    	pause(1);
    	BrowserNavigate().forward();
    	pause(1);
    	BrowserNavigate().to("https://www.google.com/");   	
 
    	try {
    		URL myURL = new URL("http://www.softwareautomationengineer.com/demo-site");
			BrowserNavigate().to(myURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	pause(2);
    	
    	BrowserNavigate().refresh();
    	pause(2);

    }
	


}
