package com.ktsapi.enums;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.core.TestDriverProvider;
import com.ktsapi.webdrivers.ChromeDriverManager;
import com.ktsapi.webdrivers.FirefoxDriverProvider;
import com.ktsapi.webdrivers.KandyWebDriverManager;

public enum Browsers {
	CHROME("chrome",ChromeDriverManager.class),
	FIREFOX("firefox",FirefoxDriverProvider.class),
	UNDEFINED("undefined",ChromeDriverManager.class); // TODO : need to check this better remove this class parameter
//	IE("internet explorer");
	
	final String name;
	final Class driverClass;
	//final String defaultDriver;

	
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
			driver = new ChromeDriverManager().get();
			break;
		case FIREFOX:
			driver = new FirefoxDriverProvider().get();
			break;
		default:
			driver = new ChromeDriverManager().get();
		}
		
//		if (capabilities.getBrowserName().equals(Browsers.CHROME.getBrowserName())) {
//			driver = new ChromeDriverManager().get();
//		} else if (capabilities.getBrowserName().equals(Browsers.FIREFOX.getBrowserName())) {
//			driver = new FirefoxDriverProvider().get();
//		}
////		else if(capabilities.getBrowserName().equals(Browsers.IE.getBrowserName())) {
////			// TODO
////		}
//		else {
//			System.out.println(capabilities.getBrowserName() + " Browser not support ");
//		}
		
		driver.manage()
				.timeouts()
				.pageLoadTimeout(TestInitializr.getPageLoadTimeout(), TimeUnit.SECONDS)
				.setScriptTimeout(TestInitializr.getScriptTimeout(), TimeUnit.SECONDS)
				.implicitlyWait(TestInitializr.getImplicitlyWaitTime(), TimeUnit.SECONDS);
		return driver;

	}
}
