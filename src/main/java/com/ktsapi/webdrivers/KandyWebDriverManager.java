package com.ktsapi.webdrivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.core.Const;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.exceptions.ConfigFileNotFoundException;

import java.nio.file.*;

public abstract class KandyWebDriverManager implements WebDriverManager{
	
	//protected static final Logger WEB_DRIVER_PROVIDER_LOG = Logger.getLogger(KandyWebDriverManager.class);
	protected static final Logger WEB_DRIVER_PROVIDER_LOG = LogManager.getLogger(KandyWebDriverManager.class);
	
//	public DesiredCapabilities getCapabilities() {
//		return DesiredCapabilities.chrome();
//	}
	
	String getBrowserProtableRootPath(String browserPropertyKey) throws FileNotFoundException, IOException {	
		File configFile = getConfigFile();		
		Properties properties = new Properties();		
		properties.load(new FileInputStream(configFile.getPath()));	
		return properties.getProperty(browserPropertyKey);
	}
	
	URL getGridHubURL() {
		// TODO : Handle logs and exceptions
		try {
			String hubURLFromTestConfig = getGridHubURIFromConfig();			
			if(hubURLFromTestConfig==null) {
				hubURLFromTestConfig =TestInitializr.getTestConfiguration().getGridHubURL(); // "http://localhost:4444/wd/hub";	
			}			
			URI hubURI = new URI(hubURLFromTestConfig).resolve(Const._GridHubRelativeURL);
			return hubURI.toURL();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
		
	}
	
	String getGridHubURIFromConfig() throws IOException {
		File configFile = getConfigFile();		
		Properties properties = new Properties();		
		try {
			properties.load(new FileInputStream(configFile.getPath()));
			return properties.getProperty(Const._GridHubURL);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}	
		
		//return null;
	}
	
	public boolean isRequestedBrowserVersinExist(String browserPropertyKey, String requestedVersion) throws FileNotFoundException, IOException{
		String browserPortablePath = getBrowserProtableRootPath(browserPropertyKey);
		File protableBrowsersFolder = new File(browserPortablePath);
		if(!protableBrowsersFolder.exists()) {
			throw new ConfigFileNotFoundException("Cannot find defined portable browser in location "+browserPortablePath);		
		}
		
		File[] listOfVersionFolders = protableBrowsersFolder.listFiles();
		for (File file : listOfVersionFolders) {
			if (file.isDirectory()) {
				if (file.getName().equals(requestedVersion)) {					
					return true;
				}
		      }
		}
		return false;
	}
	
	String getWebDriverPathOf(Browsers browser) throws UnsupportedEncodingException{
		URL driverPath = KandyWebDriverManager.class.getResource("/drivers/"+browser.getBrowserName());
		String decodedDriverPath = URLDecoder.decode(driverPath.getPath(), "UTF-8");
		return decodedDriverPath;
	}
	
	File getConfigFile() {
		Path path = Paths.get(System.getProperty(Const.USER_HOME), Const.CONFIG_FOLDER_NAME,Const.CONFIG_FILE_NAME);
		File configFile = new File(path.toString());
		if(!configFile.exists()) {
			throw new ConfigFileNotFoundException("Cannot find required configuratino files in location "+path.toString());
		}
		return configFile;
	}
	
	protected boolean canExecuteMachineInstalledBrowser(String installedBrowserVersion) {
		
		String desiredVersionPart1 = null;
		String desiredVersionPart2 = null;
		String installedVersionPart1 = null;
		String installedVersionPart2 = null;
		//boolean isDesiredBrowserExists = false;
		String desiredBrowserVersion = TestInitializr.getDesiredCapabilities().getVersion();
		
		if(installedBrowserVersion!=null) {
			if(installedBrowserVersion.contains(".")) {
				installedVersionPart1 = installedBrowserVersion.split("\\.")[0];
				installedVersionPart2 = installedBrowserVersion.split("\\.")[1];
			}else {
				installedVersionPart1 = installedBrowserVersion;
			}
		}
		
		/*
		 * When desired browser is undefined system run on machine installed chrome version as default
		 */
		if(desiredBrowserVersion.equals("UNDEFINED")) {
			desiredVersionPart1 = installedVersionPart1;
			desiredVersionPart2 = installedVersionPart2;
		}else {
			if(desiredBrowserVersion.contains(".")) {
				desiredVersionPart1 = desiredBrowserVersion.split("\\.")[0];
				desiredVersionPart2 = desiredBrowserVersion.split("\\.")[1];			
			}else {
				desiredVersionPart1 = desiredBrowserVersion;
			}
		}

		
		// check for installed versions
		if (installedBrowserVersion != null) {
			if (installedVersionPart1.contains(desiredVersionPart1) && desiredVersionPart2 == null) {
				//isDesiredBrowserExists = true;
				// TODO : need log, desired browser is same as installed  browsers
				//return getMachineInstalledChromeBinary(options);
				return true;
			} else if (installedVersionPart1.contains(desiredVersionPart1)
					&& installedVersionPart2.contains(desiredVersionPart2)) {
				//isDesiredBrowserExists = true;
				// TODO : need log, desired browser is same as installed  browsers
				//return getMachineInstalledChromeBinary(options);
				return true;
			}
		}
		
		return false;
	}
}
