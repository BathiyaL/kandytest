package com.katf.actons;

import static com.ktsapi.CommonActions.*;
import static com.ktsapi.WebActons.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Pause;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage1;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		baseUrl = "http://www.softwareautomationengineer.com/demo-site/web-elements-page-1.html",
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME
		//implicitlyWaitTime = 5,
		//pageLoadTimeout = 30000
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		//chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class IFrameTest {
	
	@BeforeTest
	public void goToDemoPage() {	      
	      OpenBrowser();	     
	      //GoTo(baseUrl());
	 }
	
	public static String ALERT_TEXT = "clickMeButton";
	@Test
	public void clickActionTest() {
		//GoTo("http://play.krypton.infor.com/reg-step2.html");
		//FindElement(By.id("companyName"));

		
		
		//GoTo("http://play.krypton.infor.com/demopage.html");
		GoTo(baseUrl());
		WebElementPage1 page = getWebPage(WebElementPage1.class);
		
		page.playEmailTextBoxElm.type("ABCDEFGHI");
		page.playSizeElm2.type("555");
		
//		page.playEmailTextBoxElm.type("bathiy@play.com");
//		page.playColorElm.type("BLUE");
//		page.playSizeElm.type("555");
//		page.playEmailTextBoxElm.type("sae@play.com");
//		
//		FindElement(By.name("emaiTextBox")).type("bathiy@play.com");
//		FindElement(By.id("password")).type("pwd1234567");
//		
//		//SwitchTo().frame(1);
//		SwitchTo().frame(FindElement(By.name("frame2")));
//		//SwitchTo().frame($(By.name("frame2")));
//		FindElement(By.name("color")).type("RED");
//		SwitchTo().frame(0);
//		FindElement(By.name("size")).type("666");
//		SwitchTo().defaultContent();
//		FindElement(By.name("emaiTextBox")).type("sam@play.com");
		
		pause(5);
	}

}