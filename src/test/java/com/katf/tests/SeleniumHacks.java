package com.katf.tests;

import static com.ktsapi.WebActons.FindElement;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.driver;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		baseUrl = "http://demoqa.taas.infor.com",
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)

public class SeleniumHacks {
	
	@Test
	public void test() {	
			
		OpenBrowser();
		GoTo("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_button_disabled"); // this page is time out need to manually stop, good to test page loading
		
		driver().switchTo().frame("iframeResult"); // not highlvele action for switchTo frame
		
		
		// <button type="button" disabled="">Click Me!</button>
		
		//TODO : is this same as isDisplavyed, remove this from baseWeblementImpl
		boolean isVisible = FindElement(By.xpath("//button[text()='Click Me!'] ")).isVisible();
		System.out.println("isVisible : " + isVisible);
		
		//Is this element displayed or not? This method avoids the problem of having to parse an
		boolean isDisplayed = FindElement(By.xpath("//button[text()='Click Me!'] ")).isDisplayed();		
		
		
		//Is the element currently enabled or not? This will generally return true for everything but disabled input elements.
		boolean isEnabled = FindElement(By.xpath("//button[text()='Click Me!'] ")).isEnabled();
		
		boolean isSelected = FindElement(By.xpath("//button[text()='Click Me!'] ")).isSelected();
		
		
		System.out.println("isDisabled : " + isDisplayed);
		System.out.println("isEnabled : " + isEnabled);
		System.out.println("isSelected : " + isSelected);
	}

}
