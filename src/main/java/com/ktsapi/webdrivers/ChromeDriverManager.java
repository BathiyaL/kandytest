package com.ktsapi.webdrivers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.ktsapi.contexts.WebDriverDefaults;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.exceptions.WebDriverProviderException;
import com.ktsapi.utils.WindowsRegistryUtils;
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
		
		String desiredBrowserVersion = TestInitializr.getDesiredCapabilities().getVersion();
		installedChromeVersions = getInstalledChromeVersion();
	
		boolean isDesiredBrowserExists = false;
		//String requiredBrowserVersion = null;
		
		
		if(desiredBrowserVersion.equals("UNDEFINED")) {
			// runs on default browser
			installedChromeVersions = "-1";			
			isDesiredBrowserExists = true;
			return getMachineInstalledChromeBinary();
		} else {
			if(installedChromeVersions==null) { // probably exception occur while getting version from registry
				// runs on default browser with warning message saying due to error try on default browser
				WEB_DRIVER_PROVIDER_LOG.warn( "Couldn't get browser VERSION of installed Browser, but trying to launch installed browser. This can be mismathc with your requested browser version");
				installedChromeVersions = "-1";			
				isDesiredBrowserExists = true;
				return getMachineInstalledChromeBinary();
			}else {
				if(canExecuteMachineInstalledBrowser(installedChromeVersions)) {
					isDesiredBrowserExists = true;
					//requiredBrowserVersion = installedChromeVersions;
					return getMachineInstalledChromeBinary();
				}
			}
		}
		

		if(!isDesiredBrowserExists) {
			// TODO : Lookup in  portable browser stack
		}
		
		if(!isDesiredBrowserExists) {
			WEB_DRIVER_PROVIDER_LOG.error("Installed chrome browser version is " + installedChromeVersions + ". Desired browser version is " + desiredBrowserVersion);
			throw new ConfigFileNotFoundException("Desired chrome browser version " + desiredBrowserVersion + " is not installed. Please configure browser version you desired or use already isntalled browser version." );		
		}
		

		return null;
	}
	
	private WebDriver getMachineInstalledChromeBinary() {
		setSystemProperty(installedChromeVersions);
		return new ChromeDriver(getChromeOptions());
	}
	
	/*
	 * Getting machine installed chrome version from windows registry.
	 */
	private String getInstalledChromeVersion() {
		try {
			return WindowsRegistryUtils.getRegistryKeyDataMap(WindowsRegistryUtils.WIN_GC_REG_QUERY,"version").get("data");
		} catch (IOException e1) {
			WEB_DRIVER_PROVIDER_LOG.error("Error occuer while getting Installed chrome browser version from windows registry");
			// TODO : Need to check do we need to throw an exception
		}
		
		return null;
	}
	
	private void setSystemProperty(String requiredBrowserVersion) {
		String chromeDriverPath = "";
		try {
			chromeDriverPath = getWebDriverPathOf(TestInitializr.getTestConfiguration().getBrowser());
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		
		String winBitVersion = "win32" ;// getWindowsBitVersion() : currently chromdriver has only 32 bit version
		String chromeDriverVersion = getChromeDriverVersion(requiredBrowserVersion);
		String requiredChromDriverFileName = "chromedriver_" + winBitVersion + "_" + chromeDriverVersion + ".exe";	
		
		File file =  new File(chromeDriverPath,requiredChromDriverFileName);
		System.setProperty("webdriver.chrome.driver",file.getPath());
	}
	private ChromeOptions getChromeOptions() {		
		ChromeOptions options = new ChromeOptions();
		String[] optionsFromTestConfig = TestInitializr.getTestConfiguration().getChromeOptions();		
		if(optionsFromTestConfig.length==1 && optionsFromTestConfig[0].equals("UNDEFINED")) {
			options.addArguments(WebDriverDefaults.CHROME_OPTIONS);//default chrome options			
		}else {
			options.addArguments(optionsFromTestConfig);			
		}
		
		return options;
	}
	
//	private String getWindowsBitVersion() {
//		if(is64bitversion()) {
//			return "win64";
//		}else {
//			return "win32";
//		}
//	}
//	private boolean is64bitversion() {
//		boolean is64bit = false;
//		if (System.getProperty("os.name").contains("Windows")) {
//		    is64bit = (System.getenv("ProgramFiles(x86)") != null);
//		} else {
//		    is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
//		}
//		return is64bit;
//	}
	
	private String getChromeDriverVersion(String chromeBrowserVersion) {
		/*
		 * References : http://chromedriver.chromium.org/
		 */
		// NOTE : currently chromedriver does releases based on only major version, but it can depend on other version parts as well if so we may need to change the logic
		int majorPart = Integer.parseInt(chromeBrowserVersion.split("\\.")[0]);
		String chromeDriverVersion = null;
			switch(majorPart) {
				case 71 : chromeDriverVersion="2_46_628402";
				break;  
				case 72 : chromeDriverVersion="2_46_628402";
				break;  
				case 73 : chromeDriverVersion="73_0_3683_68";
				break;  
				case 74 : chromeDriverVersion="74_0_3729_6";
				break;  
				case 75 : chromeDriverVersion="75_0_3770_8";
				break;  
				case 76 : chromeDriverVersion="76_0_3809_68";
				break;  
				case 77 : chromeDriverVersion="77_0_3865_40";
				break;
				case 78 : chromeDriverVersion="78_0_3904_70";
				break;
				case 81 : chromeDriverVersion="81_0_4044_138";
				break;
				case 83 : chromeDriverVersion="83_0_4103_39";
				break;
				case 87 : chromeDriverVersion="87_0_4280_20";
				break;
				case 90 : chromeDriverVersion="90_0_4430_24";	
				break;
				case 96 : chromeDriverVersion="96.0.4664.45";
				break;
				case -1 : chromeDriverVersion="102.0.5005.61"; // this is to maintain latest
				break;
			}
		/*
		 * chromedrive exe not exist required chrome version in the driver resource location,
		 * in its a new version you need to  update above switch logic and the also need to place chromedriver.exe in to driver resource folder
		 */
		if(chromeDriverVersion==null) {
			WEB_DRIVER_PROVIDER_LOG.error("Chromedriver exe not exist for chrome browser version " + chromeBrowserVersion);
			throw new WebDriverProviderException("Cannot find chromedriver exe for chrome browser version " + chromeBrowserVersion);
		}
		return chromeDriverVersion;
	}

}
