package com.ktsapi.core;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.FailedToSetupTestDriverException;
import com.ktsapi.mobiledrivers.AndroidDriverManager;

import io.appium.java_client.android.AndroidDriver;

public class TestDriverProvider {
	
	protected static WebDriver getWebDriver(WebDriver webDriver) {
		//System.out.println("######################## : TestDriverProvider.getWebDriver()" + TestCache.getTestConfiguration().getBrowser().name());
		TestDriver configuredTestDriver = TestInitializr.getTestConfiguration().getTestDriver();
		if(configuredTestDriver.equals(TestDriver.WEB)) {
	    	if(!isWebDriverLive(webDriver)) {
	    		webDriver = TestInitializr.getTestConfiguration().getBrowser().getNewWebDriver();	
	    		ConfigLogger.logInfo("Launched " + TestInitializr.getTestConfiguration().getBrowser() + " " + getRunningBrowserVersion(webDriver));
	    	}
		}else {
			throw new FailedToSetupTestDriverException("Configured test driver in the test[TestDriver."+configuredTestDriver+ "] cannot launch a web Driver");
		}
		
		return webDriver;
		
	}
	
	protected static boolean isWebDriverLive(WebDriver webDriver) {
		if(null == webDriver || hasWebDriverrQuit(webDriver)) {
			return false;
		}
		return true;
	}
	
    private static boolean hasWebDriverrQuit(WebDriver driver) {
    	if(((RemoteWebDriver)driver).getSessionId() == null) {
    		return true;
    	}
    	
    	/*
    	 * if user hit driver.close()/CloseBrowserTab() on last browser window, then then remote deriver will returns the session id instead null
    	 * but if we invoke an action it will throw NoSuchSessionException and here we are using this to verify w is closed or not, we catch it and quit driver again 
    	 */
    	return quitWebDriverIfClosed(driver);

    }

    /*
     * TODO: in latest version getting java.net.SocketException chekc this 
     */
    private static boolean quitWebDriverIfClosed(WebDriver driver) {
    	int noOfHandles = 0;
    	try {
    		noOfHandles = driver.getWindowHandles().size();    		
    	}catch(NoSuchSessionException ex ) {    		
    		try {
    			driver.quit();
    		}catch(Exception ex2) {
    			// firefox gives exception when try to quite browser even a session id is exist after close the last tab
    		}
    		
    		return true;
    	}
    	// if no of handles are 0 can consider as close
    	if(noOfHandles==0) {
    		driver.quit();
    		return true;
    	}
    		
    	return false;
    }
    
    /*
     * TODO : Need to check whether this support for other browsers as well. 
     * works  for Chrome
     */
    protected static String getRunningBrowserVersion(WebDriver driver) {
		Capabilities capabilities = ((RemoteWebDriver)driver).getCapabilities();
		String strBrowserVersion = capabilities.getBrowserVersion().toString();
		if (strBrowserVersion.equals("")){
			strBrowserVersion = capabilities.getCapability("browserVersion").toString();
		}
		return strBrowserVersion;    	
    }
    
	protected static AndroidDriver getMobileDriver() {
		//System.out.println("######################## : TestDriverProvider.getWebDriver()" + TestCache.getTestConfiguration().getBrowser().name());
		TestDriver configuredTestDriver = TestInitializr.getTestConfiguration().getTestDriver();
		if(configuredTestDriver.equals(TestDriver.MOBILE_ANDROID)) {
			return new AndroidDriverManager().get();
			
		}else {
			throw new FailedToSetupTestDriverException("Configured test driver in the test[TestDriver."+configuredTestDriver+ "] cannot launch a web Driver");
		}
		
	}

}
