package com.ktsapi.webdrivers;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.exceptions.WebDriverProviderException;

public class FirefoxDriverProvider extends KandyWebDriverManager {

	String firefoxPortableVersion = "46.0"; //TODO:Need to get from script initiation
	Boolean isUseMachineInstalledFirefox = true; //TODO:Need to get form script initiation
	String installedFirefoxVersions = null;
	
	@Override
	public WebDriver get() {
		
		// Execution on GRID	
		/* TODO : selenium 4 has removed DesiredCapabilities.firefox(); need a find how to execute on grid from selenium4
		 * if(TestCache.getTestConfiguration().getExecutionMode().equals(ExecutionMode.
		 * GRID)) { try { DesiredCapabilities cap = DesiredCapabilities.firefox();
		 * 
		 * return new RemoteWebDriver(getGridHubURL(), cap); }
		 * catch(UnreachableBrowserException e) { WEB_DRIVER_PROVIDER_LOG.
		 * error("Could not start a new session. Possible causes are invalid address of the grid hub url or browser start-up failure."
		 * ); throw new WebDriverProviderException(e.getMessage()); } }
		 */
				
		return getMachineInstalledFirefoxBinary();
	}

	private WebDriver getMachineInstalledFirefoxBinary() {
		setSystemProperty();
		// TODO : need to pass Firefox options
		return new FirefoxDriver();
	}
	
	private void setSystemProperty() {
		File file = null;
		try {
			file = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
			
		} catch (UnsupportedEncodingException e) {
			throw new WebDriverProviderException(e.getMessage());
		}
		System.setProperty("webdriver.gecko.driver",file.getPath());
	}

}
