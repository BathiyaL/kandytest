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
		//implicitlyWaitTime = 5,
		//pageLoadTimeout = 30000
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		//chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class ClickAction {
	
	@BeforeTest
	public void goToDemoPage() {	      
	      openBrowser();	      
	 }
	
	public static String ALERT_TEXT = "clickMeButton";
	@Test
	public void clickActionTest() {
		goTo("http://www.softwareautomationengineer.com/demo-site/web-elements-page-1.html");
		WebElementPage1 webElementPage1 = getWebPage(WebElementPage1.class);
		
		webElementPage1.clickMe1.click();		
		assertThat(alert().getText()).describedAs("page.element.click()").isEqualTo(ALERT_TEXT);
		alert().accept();
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :: " + FindElement(webElementPage1.clickMe1.getByLocator()).getFieldName());
		click($(webElementPage1.clickMe1.getByLocator()));
		assertThat(alert().getText()).describedAs("Click(BaseWebElement element)").isEqualTo(ALERT_TEXT);
		alert().accept();
			
		findElement(webElementPage1.clickMe1.getByLocator()).click();
		assertThat(alert().getText()).describedAs("FindElement(By locator).click()").isEqualTo(ALERT_TEXT);
		alert().accept();
		
		webElementPage1.webLink1.click();
		assertThat(alert().getText()).describedAs("webLink1.click()").isEqualTo("Web Link 1");
		alert().accept();
		
		webElementPage1.clickMeButton2.click();
		assertThat(alert().getText()).describedAs("page.element.click() - > when element not in viewport").isEqualTo("clickMeButton2");
		alert().accept();

		findElement(webElementPage1.clickMe1.getByLocator()).click();
		assertThat(alert().getText()).describedAs("page.element.click()").isEqualTo(ALERT_TEXT);
		alert().accept();
	}

}
