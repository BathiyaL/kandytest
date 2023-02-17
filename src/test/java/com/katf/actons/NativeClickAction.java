package com.katf.actons;

import static com.ktsapi.WebActons.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage1;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		//chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class NativeClickAction {
	
	@BeforeTest
	public void goToDemoPage() {	      
	      OpenBrowser();	      
	 }
	 
	public static String ALERT_TEXT = "clickMeButton";
	@Test
	public void clickActionTest() {
		GoTo("http://www.softwareautomationengineer.com/demo-site/web-elements-page-1.html");
		WebElementPage1 webElementPage1 = getWebPage(WebElementPage1.class);
		webElementPage1.clickMe1.nativeClick();
		assertThat(Alert().getText()).describedAs("page.element.click()").isEqualTo(ALERT_TEXT);
		Alert().accept();
		
		NativeClick($(webElementPage1.clickMe1.getByLocator()));
		assertThat(Alert().getText()).describedAs("Click(BaseWebElement element)").isEqualTo(ALERT_TEXT);
		Alert().accept();
			
		FindElement(webElementPage1.clickMe1.getByLocator()).nativeClick();
		assertThat(Alert().getText()).describedAs("FindElement(By locator).click()").isEqualTo(ALERT_TEXT);
		Alert().accept();
		


	}

}
