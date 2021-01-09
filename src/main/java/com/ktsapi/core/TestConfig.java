package com.ktsapi.core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ktsapi.actions.core.ActionsLogger;
import com.ktsapi.exceptions.TestConfigException;

public class TestConfig {
	
	public static void init() {
		Map<String, Object> map = new HashMap<String, Object>();
		TEST_CONFIG_MAP.set(map);
	}
	
	//private static final Logger LOG = Logger.getLogger(ActionsLogger.class);
	
	public static final String TEST_LOG = "TestConfigKey_test.logger"; //logger for the test life cycle
	public static final String TEST_ACTION_LIST = "TestConfigKey_test.action.list"; 
	
	public static final String WEB_DRIVER = "TestConfigKey_web.driver"; 
	
	
	protected static final ThreadLocal<Map<String, Object>> TEST_CONFIG_MAP = new ThreadLocal<Map<String, Object>>();

	public static WebDriver getDriver() {

		if(!isTestConfigInUse()) {
			init();
		}
		
		WebDriver webDriver = getFromTestConfigMap(WEB_DRIVER);
		
		if(webDriver==null) {
			System.setProperty("webdriver.chrome.driver","C:\\selenium\\resources\\chromedriver.exe");
			webDriver = new ChromeDriver();
			setToTestConfigMap(WEB_DRIVER,webDriver);
		}
		return webDriver;
	}
	
	
    public static final boolean isTestConfigInUse() {
    	return TEST_CONFIG_MAP.get() != null;
    }
	
    public static final void setToTestConfigMap(String key, Object value) {
    	Map<String, Object> testConfigMap = TEST_CONFIG_MAP.get();
    	if(testConfigMap != null) {
    		testConfigMap.put(key, value);
    	}
    }
    
	@SuppressWarnings("unchecked")
	public static final <X> X getFromTestConfigMap(String key) {
        Map<String, Object> testConfigMap = TEST_CONFIG_MAP.get();
        if (testConfigMap != null) {
            return (X) testConfigMap.get(key);
        }
        return null;
    }
	
    public static Logger getTestLogger() {
    	if(isTestConfigInUse()){
        	if(getFromTestConfigMap(TEST_LOG)!=null) {
        		return getFromTestConfigMap(TEST_LOG);
        	}
        	Logger testLogger = Logger.getLogger("ACTIONS");
        	setToTestConfigMap(TEST_LOG,testLogger);
            return getFromTestConfigMap(TEST_LOG);
    	}
    	throw new TestConfigException("TestLogger has called before TestConfig init");
    }
    
    public static List<Object> getTestActionsList(){
    	if(isTestConfigInUse()){
        	if(getFromTestConfigMap(TEST_ACTION_LIST)!=null) {        		
        		return getFromTestConfigMap(TEST_ACTION_LIST);
        	}
        	List<Object> actionsList = new ArrayList<Object>();
        	setToTestConfigMap(TEST_ACTION_LIST,actionsList);
            return getFromTestConfigMap(TEST_ACTION_LIST);
    	}
    	
    	throw new TestConfigException("TEST_ACTION_LIST has called before TestConfig init");
    }

}
