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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ktsapi.core.Const;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.exceptions.WebDriverNotFoundException;

public abstract class KandyWebDriverManager implements WebDriverManager{
	protected static final Logger WEB_DRIVER_PROVIDER_LOG = LogManager.getLogger(KandyWebDriverManager.class);

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
	
	File getWebDriverPathOf(Browsers browser) throws UnsupportedEncodingException{
		String driverPath;
		switch(browser) {
		  case CHROME:
		  case CHROME_HEADLESS:
			  driverPath = TestInitializr.getTestConfigObj().getWebDrivers().getChrome().getDriverPath();
		    break;
		  case FIREFOX:
		  case FIREFOX_HEADLESS:
			  driverPath = TestInitializr.getTestConfigObj().getWebDrivers().getFirefox().getDriverPath();
		    break;
		  default:
			  driverPath = TestInitializr.getTestConfigObj().getWebDrivers().getChrome().getDriverPath();
		}

		URL urlPath = KandyWebDriverManager.class.getResource(driverPath);
		
		if(urlPath==null) {
			throw new WebDriverNotFoundException("Cannot find Webdriver in given config path : " + driverPath);
		}

		return new File(URLDecoder.decode(urlPath.getPath(), "UTF-8"));
	}
	
	File getConfigFile() {
		Path path = Paths.get(System.getProperty(Const.USER_HOME), Const.CONFIG_FOLDER_NAME,Const.CONFIG_FILE_NAME);
		File configFile = new File(path.toString());
		if(!configFile.exists()) {
			throw new ConfigFileNotFoundException("Cannot find required configuratino files in location "+path.toString());
		}
		return configFile;
	}

}
