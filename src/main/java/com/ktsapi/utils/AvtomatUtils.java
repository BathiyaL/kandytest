package com.ktsapi.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.UUID;

import com.ktsapi.core.Const;
import com.ktsapi.exceptions.AndriodDriverManagerException;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.mobile.AndriodDriverManagerObject;
import com.ktsapi.mobile.AndroidDriverManager;
import com.ktsapi.mobile.AppiumServiceBuilderObject;
import com.ktsapi.webdrivers.KandyWebDriverManager;
public class AvtomatUtils {
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String configFileNotFoundExceptionMessage = "Cannot find "+Const.RESOURCE_FOLDER_LOCATION+Const.TEST_API_CONFIG_FOLDER_NAME+"/"+Const.TEST_API_CONFIG_PROPERTY_FILE_NAME+ ". If file not exist please create.";
	
	public static String getWindowsLoggedInUser() {
		return System.getProperty("user.name");
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static LocalDateTime getCurretnTimeStamp() {
		return LocalDateTime.now();
	}
	
	public static String localDateTimeStringFormat(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(localDateTime);
	}
	
	public static Path getConfigFolderPath(){
		URL configFolderURL = AvtomatUtils.class.getClassLoader().getResource(Const.TEST_API_CONFIG_FOLDER_NAME);
		if(configFolderURL==null){
			throw new ConfigFileNotFoundException(configFileNotFoundExceptionMessage);
		}
		
		try {
			return Path.of(configFolderURL.toURI());
		} catch (URISyntaxException e) {
			throw new ConfigFileNotFoundException(e.getMessage());
		}
	}
	
	public static Properties getPropertyFile(){
		FileReader reader;
		Properties configPropertyFile = new Properties(); 
		
		try {
			reader = new FileReader(getConfigFolderPath().resolve(Const.TEST_API_CONFIG_PROPERTY_FILE_NAME).toString());
		    try {
				configPropertyFile.load(reader);
			} catch (IOException e) {
				throw new ConfigFileNotFoundException(e.getMessage());
			}
		} catch (FileNotFoundException e) {
			throw new ConfigFileNotFoundException(configFileNotFoundExceptionMessage);
		}  

	      	    
	    return configPropertyFile;
	}
	
	public static AndriodDriverManagerObject getAndriodDriverManagerObject(){
		Properties ktestconfig = AvtomatUtils.getPropertyFile();
		AndriodDriverManagerObject admObje = new AndriodDriverManagerObject();
		admObje.setAppiumJS(new File(ktestconfig.getProperty(Const.ktestconfig_AppiumJSPath)));
		admObje.setNodeJSExecutable(new File(ktestconfig.getProperty(Const.ktestconfig_NodeJSPath)));
		admObje.setIpAddress(ktestconfig.getProperty(Const.ktestconfig_AppiumServerIPAddress));
		admObje.setPort(Integer.valueOf(ktestconfig.getProperty(Const.ktestconfig_AppiumServerIPort)));
		admObje.setMobileAppsPath(Path.of(ktestconfig.getProperty(Const.ktestconfig_MobileAppsPath)));
		admObje.setMobileChromeDriverPath(Path.of(ktestconfig.getProperty(Const.ktestconfig_MobileChromeDriverPath)));
		admObje.setEmulatorEXEPath(Path.of(ktestconfig.getProperty(Const.ktestconfig_EmulatorEXEPath)));
		admObje.setEmulatorStartingWaitTimeInSeconds(Integer.valueOf(ktestconfig.getProperty(Const.ktestconfig_EmulatorStartingWaitTimeInSeconds)));
		try {
			admObje.setAppiumServerRemoteAddress(new URL("http://"+ktestconfig.getProperty(Const.ktestconfig_AppiumServerIPAddress)+":"+ktestconfig.getProperty(Const.ktestconfig_AppiumServerIPort)));
		} catch (MalformedURLException e) {
			throw new AndriodDriverManagerException("Error occur whild construct remote address -> " + e.getMessage());
		}
		
		return admObje;
	}
}
