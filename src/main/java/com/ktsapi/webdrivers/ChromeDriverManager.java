package com.ktsapi.webdrivers;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.ktsapi.contexts.WebDriverDefaults;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.exceptions.WebDriverProviderException;
import com.ktsapi.utils.AvtomatUtils;
public class ChromeDriverManager extends KandyWebDriverManager{
	
	
	
	String installedChromeVersions = null;
	
	@Override
	public WebDriver get() {		
	
		// Execution on GRID
		if(TestInitializr.getTestConfiguration().getExecutionMode().equals(ExecutionMode.GRID)) {					
			try {				
				return new RemoteWebDriver(getGridHubURL(), getChromeOptions());
			} catch(UnreachableBrowserException e) {
				WEB_DRIVER_PROVIDER_LOG.error("Could not start a new session. Possible causes are invalid address of the grid hub url or browser start-up failure.");
				throw new WebDriverProviderException(e.getMessage());
			}
		}		
		return getMachineInstalledChromeBinary();
	}
	
	private WebDriver getMachineInstalledChromeBinary() {
		setSystemProperty();				
		return new ChromeDriver(getChromeOptions());
	}

	
	protected void setSystemProperty() {
		File file = null;
		try {
			file = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
			
		} catch (UnsupportedEncodingException e) {
			throw new WebDriverProviderException(e.getMessage());
		}
		if (AvtomatUtils.getOS().equals(TestInitializr.getSysConfigObj().getOs().getMac().getTagName())) {
			System.setProperty("webdriver.http.factory", "jdk-http-client");
		}

		if(file != null) {
			System.setProperty("webdriver.chrome.driver",file.getPath());
		}
	}
	private ChromeOptions getChromeOptions() {	
		
		if(TestInitializr.getTestConfiguration().getBrowser().equals(Browsers.CHROME_HEADLESS)) {
			return getHeadlessOptions();
		}
		ChromeOptions options = new ChromeOptions();
		String[] optionsFromTestConfig = TestInitializr.getTestConfiguration().getChromeOptions();		
		if(optionsFromTestConfig.length==1 && optionsFromTestConfig[0].equals("UNDEFINED")) {
			options.addArguments(WebDriverDefaults.CHROME_OPTIONS);//default chrome options			
		}else {
			options.addArguments(optionsFromTestConfig);			
		}
		
		return options;
	}
		
	private ChromeOptions getHeadlessOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--headless");
		
		//options.addArguments("--remote-allow-origins=*");
		
		return options;
	}
	
}
