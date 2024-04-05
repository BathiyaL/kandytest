package com.ktsapi.core;

public class Const {
	public static final String CONFIG_FOLDER_NAME = ".avtomatbot"; //TODO: change to "avtomate";
	public static final String CONFIG_FILE_NAME = "ksaf.config";
	
	//config.properties keys
	public static final String _FirefoxPortableRootPath = "FirefoxPortablePath";
	public static final String _FirefoxPortableExe = "FirefoxPortable.exe"; 
	
	// Grid properties
	public static final String _GridHubURL="gridHubURL";
	public static final String _GridHubRelativeURL="/wd/hub";
	
	// system properties
	public static final String USER_HOME = "user.home";
	
	// db properties
	public static final String DB_FILE_NAME = "kandy.db";
	
	// config file properties
	public static final String RESOURCE_FOLDER_LOCATION = "src/java/resources/";
	public static final String TEST_API_CONFIG_FOLDER_NAME = "config";
	public static final String TEST_API_CONFIG_MOBILE_FOLDER_NAME = "mobile";
	public static final String TEST_API_CONFIG_PROPERTY_FILE_NAME = "ktestconfig.properties";
	public static final String TEST_API_CONFIG_JSON_FILE_NAME = "ktestconfig.json";
	
	//Sys config
	public static final String SYS_CONFIG_JSON_FILE_NAME = "sysconfig.json";


	
	// ktestconfig.properties keys
	public static final String ktestconfig_AppiumJSPath = "AppiumJSPath";
	public static final String ktestconfig_NodeJSPath = "NodeJSPath";
	public static final String ktestconfig_AppiumServerIPAddress = "AppiumServerIPAddress";
	public static final String ktestconfig_AppiumServerIPort = "AppiumServerIPort";
	public static final String ktestconfig_MobileAppsPath = "MobileAppsPath";
	public static final String ktestconfig_MobileChromeDriverPath = "MobileChromeDriverPath";
	public static final String ktestconfig_EmulatorsPath = "EmulatorsPath";
	public static final String ktestconfig_EmulatorStartingWaitTimeInSeconds = "EmulatorStartingWaitTimeInSeconds";
	
	public static final String ktestconfig_IsDryRun = "IsDryRun";
	public static final String ktestconfig_ApplicationId = "ApplicationId";
	
	//WebDriver keys
	public static final String ktestconfig_ChromeDriverPath = "ChromeDriverPath";
	public static final String ktestconfig_FirefoxDriverPath = "FirefoxDriverPath";
	public static final int DEFAULT_WEBDRIVER_FINDELEMENT_TRIES = 5;

	
	// Custom Reports
	public static final String CUSTOM_REPORT_TEMPLATE_PATH = "com/ktsapi/testng/reports/reportTemplateV4.html";  

}
