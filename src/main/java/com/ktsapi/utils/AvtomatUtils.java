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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.core.Const;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.exceptions.AndriodDriverManagerException;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.mobile.AndriodDriverManagerObject;
import com.ktsapi.utils.sysconfig.SysConfig;
import com.ktsapi.utils.testconfig.KTestConfig;
import com.ktsapi.utils.testconfig.MobileDrivers;
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
	
	public static Properties getConfigPropertyFile(){
		FileReader reader = null;
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
		}finally {
			try {
				if(reader!=null) {
					reader.close();
				}
			} catch (IOException e) {
				ConfigLogger.logError("Error occurred when closing template reader");
			}
		}
	    return configPropertyFile;
	}
	
	public static AndriodDriverManagerObject getAndriodDriverManagerObject(){

		AndriodDriverManagerObject admObje = new AndriodDriverManagerObject();
		MobileDrivers mobileConfig = TestInitializr.getTestConfigObj().getMobileDrivers();
		admObje.setAppiumJS(new File(mobileConfig.getAppiumJSPath()));
		admObje.setNodeJSExecutable(new File(mobileConfig.getNodeJSPath()));
		admObje.setIpAddress(mobileConfig.getAppiumServerIPAddress());
		admObje.setPort(mobileConfig.getAppiumServerIPort());
		admObje.setMobileAppsPath(Path.of(mobileConfig.getMobileAppsPath()));
		admObje.setMobileChromeDriverPath(Path.of(mobileConfig.getMobileChromeDriverPath()));
		admObje.setEmulatorPath(Path.of(mobileConfig.getEmulatorsPath()));
		admObje.setEmulatorStartingWaitTimeInSeconds(mobileConfig.getEmulatorStartingWaitTimeInSeconds());

		try {
			admObje.setAppiumServerRemoteAddress(new URL("http://"+admObje.getIpAddress()+":"+admObje.getPort()));
		} catch (MalformedURLException e) {
			throw new AndriodDriverManagerException("Error occur whild construct remote address -> " + e.getMessage());
		}
		
		return admObje;
	}
	
	public static JSONObject getMobileCapabilitiesFile() throws Exception {
		JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(getConfigFolderPath().resolve(Const.TEST_API_CONFIG_MOBILE_FOLDER_NAME).resolve(TestInitializr.getTestConfiguration().getMobileCapabilitiesFileName()).toString())) {
            Object obj = parser.parse(reader);
            return (JSONObject) obj;
        } catch (Exception e) {
        	throw new AndriodDriverManagerException("Error occur whild fetching mobile capabilities json file -> " + e.getMessage());
        }
	}

	public static File getConfigJsonFile(String fileName){
		File file;
		
		try {
			file = new File(getConfigFolderPath().resolve(fileName).toString());
		} catch (NullPointerException e) {
			throw new ConfigFileNotFoundException(configFileNotFoundExceptionMessage);
		}	    
	    return file;
	}
	
	public static KTestConfig validateAndGetKTestConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        KTestConfig config = new KTestConfig();
        try {
            config = objectMapper.readValue(getConfigJsonFile(Const.TEST_API_CONFIG_JSON_FILE_NAME), KTestConfig.class);
            return config;
        } catch (IOException e) {
        	ConfigLogger.logError("#############=>"+e.getMessage());
        	throw new ConfigFileNotFoundException(configFileNotFoundExceptionMessage);
        }
	}
	
	public static SysConfig validateAndGetSysConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        SysConfig config = new SysConfig();
        try {
            config = objectMapper.readValue(getConfigJsonFile(Const.SYS_CONFIG_JSON_FILE_NAME), SysConfig.class);
            return config;
        } catch (IOException e) {
        	throw new ConfigFileNotFoundException(e.getMessage());
        }
	}
	
	public static String getOS() {
		// TODO check for other os and return
		
		try {
			String osNameProperty = TestInitializr.getSysConfigObj().getSystemProperties().getOsName();
			String[] supportOSList = TestInitializr.getSysConfigObj().getOs().getSupportTypes();
			
			String runningOS = System.getProperty(osNameProperty); // e.g. os.name
			for(String osType : supportOSList) {
				if(runningOS!=null && runningOS.toLowerCase().contains(osType)) {
					return osType;
				}
			}
		} catch (Exception e) {
			ConfigLogger.logError("Error when fetching OS from system property, return OS=UNDEFINED");
			return "UNDEFINED";
		}

		return "win"; // TODO: for now default is win
	}

}
