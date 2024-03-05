package com.katf.tests;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.GetTitle;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.ktsapi.CommonActions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.FBPage;
import com.katf.pageobjects.ShadowDomTestPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		baseUrl = "http://watir.com/examples/shadow_dom.html",
		//browserVersion="76",
		//executionMode = ExecutionMode.GRID,
		//gridHubURL = "http://localhost:4445",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class TC3_ShadowDom {

	@Test
	public void testMethod() {
		GoTo("http://watir.com/examples/shadow_dom.html");
		ShadowDomTestPage shadowPage = getWebPage(ShadowDomTestPage.class);
		String text = shadowPage.nestedText.getText();
		System.out.println("####### " + text);
		
		pause(5);

//		   WebElement shadowHost = FindElement(By.id("shadow_host")).asWebelement();
//	       SearchContext shadowRoot = shadowHost.getShadowRoot ();
//	       WebElement shadowContent = shadowRoot.findElement (By.cssSelector("#nested_shadow_host"));
//	       SearchContext shadowRootTwo = shadowContent.getShadowRoot ();
//	       String nestedText = shadowRootTwo.findElement (By.cssSelector ("#nested_shadow_content > div")).getText ();

		
//		SearchContext shadowParentElm = FindElement(By.id("shadow_host")).asWebelement().getShadowRoot().findElement(By.id("nested_shadow_host")).getShadowRoot();
//		shadowParentElm.findElement(By.id("nested_shadow_content"));
//		pause(5);
	}
}
