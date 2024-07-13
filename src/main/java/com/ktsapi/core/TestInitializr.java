package com.ktsapi.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.contexts.TestConfigurationContext;
import com.ktsapi.dto.Testplan;
import com.ktsapi.testng.TestNGConfig;
import com.ktsapi.utils.sysconfig.SysConfig;
import com.ktsapi.utils.testconfig.KTestConfig;

import io.appium.java_client.android.AndroidDriver;

public class TestInitializr {
	
	public static final String DESIRED_CAPABILITIES = "__desired.capabilities";
	public static final String TEST_CONFIGURATION = "__test.configuration";
	public static final String TEST_SUITE_FILE_PATH= "_test.suite.file.path";
	public static final String TEST_PLAN_UUID = "_test.plan.uuid";
	public static final String TEST_PLAN_NAME = "__test.plan.name"; 
	public static final String TOTOAL_TESTS_IN_TEST_PLAN_XML = "__total.tests.in.test.plan.xml"; 
	public static final String TEST_CLASS_NAME = "__test.class.name";	
	public static final String TEST_NAME = "__test.name";
	public static final String TEST_ID = "__test.id";	
	public static final String TEST_UUID = "_test.uuid";
	public static final String TEST_EXECUTED_BY = "_test.executed.by";
	public static final String WEB_DRIVER = "_web.driver";
	public static final String MOBILE_DRIVER = "_mobile.driver";
	public static final String RUNNING_BROWSER_VERSION = "_running.browser.version";
	public static final String WEB_DRIVER_IN_USE = "_web.driver.in.use"; 
	public static final String MOBILE_DRIVER_IN_USE = "_mobile.driver.in.use"; 
	public static final String TEST_LOG = "__test.logger"; //logger for the test life cycle
	public static final String TEST_ACTION_LIST = "__test.action.list"; 
	
	public static final String TEST_PLAN_OBJ = "__test.plan.obj"; 
	public static final String TEST_CONFIG_OBJ = "__test.config.obj"; 
	public static final String TEST_SYS_CONFIG_OBJ = "__test.sys.config.obj"; 
	public static final String TESTNG_CONFIG_OBJ = "__testng.config.obj"; 
	
	public static final String IMPLICITLY_WAIT_TIME = "__implicitly.wait.time";
	public static final String SCRIPT_TIMEOUT = "__script.timeout";
	public static final String PAGELOAD_TIMEOUT = "__pageload.timeout"; 
	
	public static final String PARENT_WINDOW_HANDLE = "__parent.window.handle"; 
	
	public static final String KANDY_CLIENT_TEST_PLAN_ID =  "__kandy.client.test.plan.id";
	public static final String KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID =  "__kandy.client.test.plan.automated.run.id";
	
	public static final String IS_DRY_RUN = "__is.dry.run"; 
	public static final String IS_ONE_TO_ONE_MAPPING = "__is.one.to.one.mapping";
	
	protected static final ThreadLocal<Map<String, Object>> TEST_CACHE = new ThreadLocal<>();
	
	
	// set from TestRunner
    public static void setDesiredCapabilities(DesiredCapabilities capabilities) {
    	set(DESIRED_CAPABILITIES, capabilities);
    }    
    public static void setTestConfiguration(TestConfigurationContext testConfigurationContext) {
    	set(TEST_CONFIGURATION, testConfigurationContext);
    }
    public static void setTestClassName(String testClassName) {
    	set(TEST_CLASS_NAME, testClassName);
    }
    public static void setTestName(String testName) {
    	set(TEST_NAME, testName);
    }
    public static void setTestId(String testID) {
    	set(TEST_ID, testID);
    }
    public static void setTestPlanName(String testPlanName) {
    	set(TEST_PLAN_NAME, testPlanName);
    }
    public static void setTestSuiteFilePath(Path testSuiteFilePath) {
    	set(TEST_SUITE_FILE_PATH, testSuiteFilePath);
    }
    public static void setTestPlanUUID(String testPlanUUID) {
    	set(TEST_PLAN_UUID, testPlanUUID);
    }
    public static void setKandyClientTestPlanId(String testPlanUUID) {
    	set(KANDY_CLIENT_TEST_PLAN_ID, testPlanUUID);
    }
    public static void setKandyClientTestPlanAutomatedRunId(String testPlanAutomatedRunId) {
    	set(KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID,testPlanAutomatedRunId);
    }   
    
    public static void setTestPlanObj(Testplan testPlanObj) {
    	set(TEST_PLAN_OBJ, testPlanObj);
    }
    
    public static void setTestConfigObj(KTestConfig testConfig) {
    	set(TEST_CONFIG_OBJ, testConfig);
    }
    
    public static void setSysConfigObj(SysConfig sysConfig) {
    	set(TEST_SYS_CONFIG_OBJ, sysConfig);
    }
    public static void setTestNGConfigObj(TestNGConfig testNGConfig) {
    	set(TESTNG_CONFIG_OBJ, testNGConfig);
    }
    
    // These wait times can set by user within script life cycle
    public static void setImplicitlyWaitTime(long implicitlyWaitTime) {
    	set(IMPLICITLY_WAIT_TIME, implicitlyWaitTime);
    }
    public static void setScriptTimeout(long scriptTimeout) {
    	set(SCRIPT_TIMEOUT, scriptTimeout);
    }
    public static void setPageLoadTimeout(long pageLoadTimeout) {
    	set(PAGELOAD_TIMEOUT, pageLoadTimeout);
    }
    
    public static DesiredCapabilities getDesiredCapabilities() {
        return get(DESIRED_CAPABILITIES);
    }
    public static TestConfigurationContext getTestConfiguration() {
    	return get(TEST_CONFIGURATION);
    }
    public static String getTestClassName() {
    	return get(TEST_CLASS_NAME);
    }
    public static String getTestName() {
    	return get(TEST_NAME);
    }
    public static String getTesID() {
    	return get(TEST_ID);
    }
    public static String getTestPlanName() {
    	return get(TEST_PLAN_NAME);
    }
    public static Path getTestSuiteFilePath() {
    	return get(TEST_SUITE_FILE_PATH);
    }
    public static String getTestPlanUUID() {
    	return get(TEST_PLAN_UUID);
    }    
    public static Testplan getTestPlanObj() {
    	return (Testplan)get(TEST_PLAN_OBJ);
    }
    
    public static KTestConfig getTestConfigObj() {
    	return (KTestConfig)get(TEST_CONFIG_OBJ);
    }
    
    public static SysConfig getSysConfigObj() {
    	return (SysConfig)get(TEST_SYS_CONFIG_OBJ);
    }
    public static TestNGConfig getTestNGConfigObj() {
    	return (TestNGConfig)get(TESTNG_CONFIG_OBJ);
    }
    
    public static Long getImplicitlyWaitTime() {
    	return (Long)get(IMPLICITLY_WAIT_TIME);
    }
    public static Long getScriptTimeout() {
    	return (Long)get(SCRIPT_TIMEOUT);
    }
    public static Long getPageLoadTimeout() {
    	return (Long)get(PAGELOAD_TIMEOUT);
    }
    public static String getKandyClientTestPlanId() {
    	return get(KANDY_CLIENT_TEST_PLAN_ID);
    }   
    public static String getKandyClientTestPlanAutomatedRunId() {
    	return get(KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID);
    }   
    
    
    
    public static final void set(String key, Object value) {
    	Map<String, Object> map = TEST_CACHE.get();
    	if(map != null) {
    		map.put(key, value);
    	}
    }
    
	public static final <X> X get(String key) {
        Map<String, Object> map = TEST_CACHE.get();
        if (map != null) {
            return (X) map.get(key);
        }
        return null;
    }
    
    public static final boolean isTestCacheInUse() {
    	return TEST_CACHE.get() != null;
    }
    
    // this will trigger when WebAction.OpenBrowser() called
    public static WebDriver getWebDriver() {

    	WebDriver webDriver = get(WEB_DRIVER);
    	if(!TestDriverProvider.isWebDriverLive(webDriver)) {    		
        	webDriver = TestDriverProvider.getWebDriver(get(WEB_DRIVER));
        	setParentWindowHandle(webDriver.getWindowHandle());
        	setWebDriver(webDriver);
        	setRunningBrowserVersion(webDriver);
        	setWebDriverInUse();
        	return getWebDriver();
    	}

    	return webDriver;
    }
    
    public static AndroidDriver getMobileDriver() {
    	AndroidDriver mobileDriver = get(MOBILE_DRIVER);
    	if(mobileDriver==null) {
    		mobileDriver =  TestDriverProvider.getMobileDriver();
    		setMobileDriver(mobileDriver);
    		setMobileDriverInUse();
    		return getMobileDriver();
    	}
    	return mobileDriver;
    }
    private static void setMobileDriver(AndroidDriver mobileDriver) {
    	set(MOBILE_DRIVER,mobileDriver);
    }
    
    private static void setParentWindowHandle(String windowHandle) {
    	set(PARENT_WINDOW_HANDLE,windowHandle);
    }
    
    private static void setWebDriver(WebDriver webDriver) {
    	set(WEB_DRIVER,webDriver);
    }
    private static void setWebDriverInUse() {
    	set(WEB_DRIVER_IN_USE, Boolean.TRUE);
    }
    private static void setMobileDriverInUse() {
    	set(MOBILE_DRIVER_IN_USE, Boolean.TRUE);
    }
	public static boolean isWebDriverUse() {
		return (boolean) (get(WEB_DRIVER_IN_USE) != null ? get(WEB_DRIVER_IN_USE) : Boolean.FALSE.booleanValue());
	}
	public static boolean isMobileDriverUse() {
		return (boolean) (get(MOBILE_DRIVER_IN_USE) != null ? get(MOBILE_DRIVER_IN_USE) : Boolean.FALSE.booleanValue());
	}
    
    private static void setRunningBrowserVersion(WebDriver webDriver) {
    	set(RUNNING_BROWSER_VERSION,TestDriverProvider.getRunningBrowserVersion(webDriver));
    }
    
    public static String getParentWindowHandle() {
    	return get(PARENT_WINDOW_HANDLE);
    }
    
    public static String getRunningBrowserVersion() {
    	return get(RUNNING_BROWSER_VERSION);
    }
    
    public static boolean getDryRunStatus() {
    	Boolean dryRunStatus = get(IS_DRY_RUN);
    	if(dryRunStatus==null) {
    		return true;
    	}
    	return dryRunStatus;
    }
    
    public static void setDryRunStatus(boolean isDryRun) {
    	set(IS_DRY_RUN,isDryRun);
    }
    
    public static Logger getTestLogger() {
    	if(isTestCacheInUse()){
        	if(get(TEST_LOG)!=null) {
        		return get(TEST_LOG);
        	}
        	//Logger testLogger = Logger.getLogger("ACTIONS");// Create new Logger instance TODO: consider passing script uuid as logger name
        	Logger testLogger = LogManager.getLogger("ACTIONS");
        	set(TEST_LOG,testLogger);
            return get(TEST_LOG);
    	}    	
    	return null; // TODO : handle meaningful exception
    }

    public static void resetActionsList(){
    	set(TEST_ACTION_LIST,null);
    }
    
    public static List<Object> getTestActionsList(){
    	if(isTestCacheInUse()){
        	if(get(TEST_ACTION_LIST)!=null) {        		
        		return get(TEST_ACTION_LIST);
        	}
        	List<Object> actionsList = new ArrayList<Object>();
        	set(TEST_ACTION_LIST,actionsList);
            return get(TEST_ACTION_LIST);
    	}
    	System.out.println("TEST_ACTION_LIST has called befre TestCache init");
    	return null; // TODO : handle meaningful exception
    }
    
    

}
