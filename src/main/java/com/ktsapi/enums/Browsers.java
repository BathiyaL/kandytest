package com.ktsapi.enums;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.core.TestInitializr;
import com.ktsapi.webdrivers.ChromeDriverManager;
import com.ktsapi.webdrivers.FirefoxDriverProvider;
import com.ktsapi.webdrivers.KandyWebDriverManager;
import com.ktsapi.webdrivers.SafariDriverProvider;

public enum Browsers {
	CHROME("chrome",ChromeDriverManager.class),
	FIREFOX("firefox",FirefoxDriverProvider.class),
	FIREFOX_HEADLESS("firefox-headless",FirefoxDriverProvider.class),
	CHROME_HEADLESS("chrome-headless",ChromeDriverManager.class),
	SAFARI("safari",SafariDriverProvider.class),
	UNDEFINED("undefined",ChromeDriverManager.class); // TODO : need to check this better remove this class parameter
//	IE("internet explorer");
	
	final String name;
	final Class driverClass;
	
	Browsers(String name,Class<? extends KandyWebDriverManager> driverClass){
		this.name = name ;
		this.driverClass = driverClass;
	}
	
	public String getBrowserName() {
		return name;
	}
	
	public WebDriver getNewWebDriver() {

		DesiredCapabilities capabilities =  TestInitializr.getDesiredCapabilities();	
		if(capabilities!=null) {
			if(capabilities.getBrowserName()!=null && !capabilities.getBrowserName().isEmpty()) {
				return getWebDriverBasedOnTestConfiguration();
			}else {
				// TODO : throw runtime exception saying capabilitiesgetBrowserName() cannot be null or empty
			}
		}else {
			// TODO : throw runtime exception saying capabilities not set
		}
		
		return null;
	}
	
	private WebDriver getWebDriverBasedOnTestConfiguration() {
		WebDriver driver = null;
		
		switch (TestInitializr.getTestConfiguration().getBrowser()) {
		case CHROME:
		case CHROME_HEADLESS:
			driver = new ChromeDriverManager().get();
			break;
		case FIREFOX:
		case FIREFOX_HEADLESS:
			driver = new FirefoxDriverProvider().get();
			break;
		case SAFARI:
			driver = new SafariDriverProvider().get();
			break;
		default:
			driver = new ChromeDriverManager().get();
		}

		driver.manage()
				.timeouts()
				.pageLoadTimeout(Duration.ofSeconds(TestInitializr.getPageLoadTimeout()))
				.scriptTimeout(Duration.ofSeconds(TestInitializr.getScriptTimeout()))
				.implicitlyWait(Duration.ofSeconds(TestInitializr.getImplicitlyWaitTime()));
		return driver;

	}
}
