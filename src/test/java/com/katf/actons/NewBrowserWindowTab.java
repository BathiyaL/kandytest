package com.katf.actons;

import static com.ktsapi.CommonActions.*;
import static com.ktsapi.WebActons.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.devtools.Log;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Pause;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage1;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		baseUrl = "http://www.softwareautomationengineer.com/demo-site/web-elements-page-1.html",
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.FIREFOX,
		//browserVersion = "71",
		//implicitlyWaitTime = 5,
		pageLoadTimeout = 10
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		//chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class NewBrowserWindowTab {
	
	@BeforeTest
	public void goToDemoPage() {	      
	      OpenBrowser();	     
	      //GoTo(baseUrl());
	 }
	
	public static String ALERT_TEXT = "clickMeButton";
	@Test
	public void clickActionTest() {
	
		GoTo(baseUrl());
		System.out.println("#####################  ::: " + WebDriver().getWindowHandle());
		GetNewTab("https://twitter.com/");	
//		System.out.println("#####################  ::: " + driver().getWindowHandle());
//		CloseBrowser();
		
//		OpenBrowser();
		WebElementPage1 page = GetWebPage(WebElementPage1.class);
		page.TwitterElm.click();
		
		SwitchToWindowOrTab(ParentWindowHandle());
		page.playEmailTextBoxElm.type("ABCDEFGHI");
		page.playSizeElm2.type("555");
		
		GetNewWindow("https://www.youtube.com/");
		System.out.println("#####################  ::: " + WebDriver().getWindowHandle());
		
		System.out.println("#####################ParentWindowHandle  ::: " + ParentWindowHandle());

		pause(5);
	}

}