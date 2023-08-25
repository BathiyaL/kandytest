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

import com.ktsapi.contexts.WebDriverDefaults;
import com.ktsapi.core.Const;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.exceptions.ConfigFileNotFoundException;

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
	
	String getOS() {
		// TODO chekc for other os and return
		String runningOS = System.getProperty("os.name");
		if (runningOS.startsWith("Mac")) { // move this to config file or enum
			return "mac";
		}
		return "win";
	}
	String getBitSystem() {
		// TODO check bit system from the machine, for now mac 64 not handled
		if(getOS().equals("mac")) {
			return "arm64";
		}
		return "32";
	}
	
	String getWebDriverPathOf(Browsers browser) throws UnsupportedEncodingException{
		
		if(browser.equals(Browsers.CHROME_HEADLESS)) {
			browser = Browsers.CHROME; // both chrome and chrome headless use same driver
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  : " + TestInitializr.getTestConfigObj().getWebDrivers().getChrome().getDriverPath());
		String driverPath1 = TestInitializr.getTestConfigObj().getWebDrivers().getChrome().getDriverPath();
		URL urlPath = KandyWebDriverManager.class.getResource(driverPath1);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>getPath  : " + urlPath.getPath());
		
		
		URL driverPath = KandyWebDriverManager.class.getResource("/drivers/"+browser.getBrowserName()+ "/"+getOS() +"/_"+getBitSystem()); // TODO use more efficient way to handle path
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

}
