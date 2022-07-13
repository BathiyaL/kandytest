package com.ktsapi.webdrivers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.ktsapi.core.Const;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.exceptions.WebDriverProviderException;
import com.ktsapi.utils.WindowsRegistryUtils;

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
				
		
		String desiredBrowserVersion = TestInitializr.getDesiredCapabilities().getVersion();
		installedFirefoxVersions = getInstalledFirefoxVersion();	
		boolean isDesiredBrowserExists = false;
		//String requiredBrowserVersion = null;
		
		
		if(desiredBrowserVersion.equals("UNDEFINED")) {
			// runs on default browser
			installedFirefoxVersions = "-1";			
			isDesiredBrowserExists = true;
			return getMachineInstalledFirefoxBinary();
		} else {
			if(installedFirefoxVersions==null) { // probably exception occur while getting version from registry
				// runs on default browser with warning message saying due to error try on default browser
				WEB_DRIVER_PROVIDER_LOG.warn( "Couldn't get browser VERSION of installed Browser, but trying to launch installed browser. This can be mismathc with your requested browser version");
				installedFirefoxVersions = "-1";			
				isDesiredBrowserExists = true;
				return getMachineInstalledFirefoxBinary();
			}else {
				if(canExecuteMachineInstalledBrowser(installedFirefoxVersions)) {
					isDesiredBrowserExists = true;
					//requiredBrowserVersion = installedChromeVersions;
					return getMachineInstalledFirefoxBinary();
				}
			}
		}
		

		if(!isDesiredBrowserExists) {
			// TODO : Lookup in  portable browser stack
		}
		
		if(!isDesiredBrowserExists) {
			WEB_DRIVER_PROVIDER_LOG.error("Installed chrome browser version is " + installedFirefoxVersions + ". Desired browser version is " + desiredBrowserVersion);
			throw new ConfigFileNotFoundException("Desired chrome browser version " + desiredBrowserVersion + " is not installed. Please configure browser version you desired or use already isntalled browser version." );		
		}

		return null;

	}
	
	/*
	 * Getting machine installed chrome version from windows registry.
	 */
	private String getInstalledFirefoxVersion() {
		try {
			return WindowsRegistryUtils.getRegistryKeyDataMap(WindowsRegistryUtils.WIN_FF_REG_QUERY,"CurrentVersion").get("data");
		} catch (Exception e1) {
			WEB_DRIVER_PROVIDER_LOG.error("Error occuer while getting Installed chrome browser version from windows registry");
			// TODO : Need to check do we need to throw an exception
		}
		
		return null;
	}
	
	private WebDriver getMachineInstalledFirefoxBinary() {
		setSystemProperty(installedFirefoxVersions);
		return getInstalledFirefoxIfRequested();
	}
	
	private void setSystemProperty(String requiredBrowserVersion) {
		String firefoxDriverPath = "";
		try {
			firefoxDriverPath = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		
		String winBitVersion = WindowsRegistryUtils.getWindowsBitVersion(); 
		String firefoxDriverVersion = getFirefoxDriverVersion(requiredBrowserVersion);
		String requiredFirefoxDriverFileName = "geckodriver_" + winBitVersion + "_" + firefoxDriverVersion + ".exe";	
//		
//		File file =  new File(firefoxDriverPath,requiredChromDriverFileName);
		
		File file =  new File(firefoxDriverPath,requiredFirefoxDriverFileName);
		System.setProperty("webdriver.gecko.driver",file.getPath());
	}
	
	private String getFirefoxDriverVersion(String firefoxBrowserVersion) {
		/*
		 * References : https://firefox-source-docs.mozilla.org/testing/geckodriver/Support.html
		 */
		
		int majorPart = Integer.parseInt(firefoxBrowserVersion.split("\\.")[0]);
		String firefoxDriverVersion = null;
		
		if(majorPart==-1) {
			firefoxDriverVersion = "0.26.0"; // this is for latest
		}else if(majorPart>=60) {
			firefoxDriverVersion = "0.26.0";
		}

		if(firefoxDriverVersion==null) {
			WEB_DRIVER_PROVIDER_LOG.error("Geckodriver exe not exist for Firefox browser version " + firefoxBrowserVersion);
			throw new WebDriverProviderException("Cannot find Geckodriver exe for Firefox browser version " + firefoxBrowserVersion);
		}
		return firefoxDriverVersion;
	}
	
	
	
		
	public String getPortableBrowserPath() throws FileNotFoundException, IOException{		
		if(isRequestedBrowserVersinExist(Const._FirefoxPortableRootPath,firefoxPortableVersion)){
			return (getBrowserProtableRootPath(Const._FirefoxPortableRootPath) + firefoxPortableVersion + "//" + Const._FirefoxPortableExe);
		}else {
			throw new WebDriverProviderException("Cannot find requested Firefox portable browser version "+ firefoxPortableVersion + " in location "+getBrowserProtableRootPath(Const._FirefoxPortableRootPath));		
		}	
	}
	
	FirefoxProfile firefoxProfile() throws Exception {
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		// firefoxProfile.setPreference("browser.safebrowsing.enabled",true);
		// firefoxProfile.setPreference("browser.download.dir",Paths.get(Directories.Download.get()).resolve(TestCache.getUuid()).toString());
		// System.out.println("[FirefoxDriverProvider] Opened file download
		// folder
		// "+Paths.get(Directories.Download.get()).resolve(TestCache.getUuid()).toString());
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,application/zip,text/html,"
						+ "text/plain,application/msword,application/xml,application/EDI-X12");
		return firefoxProfile;
	}

	WebDriver getFirefoxDriver(){
		String firefoxDriverPath = "";
		try {
			firefoxDriverPath = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file =  new File(firefoxDriverPath,"geckodriver_64.exe");
		System.setProperty("webdriver.gecko.driver",file.getPath());
		
		WebDriver ffDriver = null;
		ffDriver = getInstalledFirefoxIfRequested();
		if(ffDriver==null)
			ffDriver = getPortableFirefoxIfRequested();
		if(ffDriver==null)
			ffDriver = getAvtomatBotDefaultBrowser();
		
		return ffDriver;
	}
	
	WebDriver getInstalledFirefoxIfRequested() {
		try {
			if (isUseMachineInstalledFirefox)				
				return new FirefoxDriver(); // TODO :  need to think a way to pass browser option/profile
		} catch (Exception e) {
			throw new WebDriverProviderException("Exception occuser when try to get instaled Firefox :: " + e.getMessage());
		}
		return null;
	}
	
	WebDriver getPortableFirefoxIfRequested() {
		
		
		try {
			if (firefoxPortableVersion!=null || firefoxPortableVersion!="") {	
				FirefoxOptions ffOptions = new FirefoxOptions();
				ffOptions.setBinary(getPortableBrowserPath());
				return new FirefoxDriver(ffOptions);
				//options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				//return new FirefoxDriver(new FirefoxBinary(new File(getPortableBrowserPath())),firefoxProfile());  // TODO :  need to fix
				//return null;
			}
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return null;
	}
	
	WebDriver getAvtomatBotDefaultBrowser(){
			// TODO :  Decide how to send default browser when user not specified
			// also how to install a default browser at installation time
		return null;
	}
}
