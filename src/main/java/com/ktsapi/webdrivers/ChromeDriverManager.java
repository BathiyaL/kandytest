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
		String chromeDriverPath = "";
		try {
			if(TestInitializr.getTestConfiguration().getBrowserVersion().equals(WebDriverDefaults.BUILT_IN_BROWSER_VERSION)) {
				chromeDriverPath = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
			}else {
				// TODO : get from drivers folder
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		File file = null;
		//String runningOS = System.getProperty("os.name");
		if (getOS().startsWith("mac")) { // move this to config file or enum
			file =  new File(chromeDriverPath,WebDriverDefaults.BUILT_IN_CHROME_DRIVER_FILE_NAME_MAC);
			System.setProperty("webdriver.http.factory", "jdk-http-client");
		} else {  // TODO : if needed handle win and other os types
			file =  new File(chromeDriverPath,WebDriverDefaults.BUILT_IN_CHROME_DRIVER_FILE_NAME_WIN);

		}

		System.setProperty("webdriver.chrome.driver",file.getPath());
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
