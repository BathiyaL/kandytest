package com.ktsapi.testng;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.contexts.TestConfigurationContext;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.contexts.WebDriverDefaults;
import com.ktsapi.core.TestContext;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.dto.Testplan;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.TestClassNotFoundException;
import com.ktsapi.utils.sysconfig.SysConfig;
import com.ktsapi.utils.testconfig.KTestConfig;

public class TestngTestContext implements TestContext{
	
	private ITestResult result;
	
	public TestngTestContext(final ITestResult result) {
		this.result = result;
	}

	/*
	 * These capabilities are as in selenium DesiredCapabilities
	 * https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities
	 */
	//Used by the selenium server for browser selection
	String baseUrl = null;
	Browsers browser ;
	String browserName = null;
	String browserversion = null;
	Platform platform = Platform.ANY;
	String chromeOptions[];
	
	// Avotomat attributes
	TestDriver testDriver;
	ExecutionMode executionMode;
	String gridHubURL;
	long implicitlyWaitTime;
	long scriptTimeout;
	long pageLoadTimeout;
	
	String mobileApp;
	String mobileDeviceName;
	String mobileCapabilitiesFileName;
	
	boolean isDryRun;

	TestConfigurationContext testConfigurationContext ;
	
	@Override
	public DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				
		if(hasTestConfigurationAnnotation()) {
			getTestCofigurationFromScript();
		}
		desiredCapabilities.setBrowserName(browserName);
		desiredCapabilities.setVersion(browserversion);
		desiredCapabilities.setPlatform(platform);
		
		return desiredCapabilities;
	}
	
	private void getTestCofigurationFromScript() {
				
		Testplan testPlan = getTestPlanObj();
		TestConfiguration testConfig = getTestInstanceClass().getAnnotation(TestConfiguration.class);
		
		baseUrl = getValueOfStandardParameter(TestSuiteParameters.BASE_URL, testPlan,testConfig);		
		browserName = getValueOfStandardParameter(TestSuiteParameters.BROWSER, testPlan,testConfig);
		if(!browserName.isEmpty()) {
			browser = Browsers.valueOf(browserName.toUpperCase());

		}
		// TODO : following need to add to Test suite parameters
		browserversion = testConfig.browserVersion();
		testDriver = testConfig.testDriver();
		chromeOptions = testConfig.browserOptions();
		executionMode = testConfig.executionMode();
		gridHubURL=testConfig.gridHubURL();
		if(testConfig.browserVersion().equals("UNDEFINED")) {
			browserversion = WebDriverDefaults.BUILT_IN_BROWSER_VERSION;
		}else {
			browserversion = testConfig.browserVersion();
		}
		
		chromeOptions = testConfig.browserOptions();
		

		implicitlyWaitTime=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.IMPLICITLY_WAIT_TIME, testPlan,testConfig)); //testConfig.implicitlyWaitTime();
		scriptTimeout=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.SCRIPT_TIMEOUT, testPlan,testConfig));
		pageLoadTimeout=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.PAGE_LOAD_TIMEOUT, testPlan,testConfig));
		
		// Mobile parameters
		// TODO : currently not decided to add mobile configs to suite level parameters
		mobileApp = testConfig.mobileApp();
		mobileDeviceName= testConfig.mobileDeviceName();
		mobileCapabilitiesFileName = testConfig.mobileCapabilitiesFileName();
		
				
		testConfigurationContext = new TestConfigurationContext();
		testConfigurationContext.setBaseUrl(baseUrl);		
		testConfigurationContext.setBrowser(browser);
		
		testConfigurationContext.setTestDriver(testDriver);
		testConfigurationContext.setBrowserVersion(browserversion);
		testConfigurationContext.setBrowserOptions(chromeOptions);
		testConfigurationContext.setExecutionMode(executionMode);
		testConfigurationContext.setGridHubURL(gridHubURL);
		testConfigurationContext.setImplicitlyWaitTime(implicitlyWaitTime);
		testConfigurationContext.setScriptTimeout(scriptTimeout);
		testConfigurationContext.setPageLoadTimeout(pageLoadTimeout);
		
		testConfigurationContext.setMobileApp(mobileApp);
		testConfigurationContext.setMobileDeviceName(mobileDeviceName);
		testConfigurationContext.setMobileCapabilitiesFileName(mobileCapabilitiesFileName);

		String testInstanceName = result.getInstanceName();
		testConfigurationContext.setTestClassName(testInstanceName.substring(testInstanceName.lastIndexOf('.') + 1));
	}
	
	// if suite level parameter is undefined get from test config
	private String getValueOfStandardParameter(String parameter,Testplan testPlan , TestConfiguration testConfig) {
		
		String suiteLevelParameterValue=null;
		String testLevelParameterValue = getTestLevelParametersMap().get(parameter);
		String returnParameterValue = testLevelParameterValue;
		
		switch(parameter) {
			case TestSuiteParameters.BASE_URL : {
				suiteLevelParameterValue = testPlan.getParm_baseUrl();
				break;
			}
			case TestSuiteParameters.IMPLICITLY_WAIT_TIME : {
				suiteLevelParameterValue = testPlan.getParm_implicitlyWaitTime();							
				break;
			}
			case TestSuiteParameters.PAGE_LOAD_TIMEOUT : {
				suiteLevelParameterValue = testPlan.getParm_pageLoadTimeout();
				break;
			}
			case TestSuiteParameters.SCRIPT_TIMEOUT : {
				suiteLevelParameterValue = testPlan.getParm_scriptTimeout();
				break;
			}
			case TestSuiteParameters.BROWSER : {
				suiteLevelParameterValue = testPlan.getBrowser();
				break;
			}
		}
		
		if (suiteLevelParameterValue != null && !suiteLevelParameterValue.toUpperCase().equals(TestSuiteParameters.UNDEFINED)) { // if not defined in suite level it will set as "UNDEFINED"
			if (!suiteLevelParameterValue.equals(testLevelParameterValue)) {
				/*
				 * different means it gives priority to test level parameter
				 */
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from test-level suite parameters");
			} else {
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from suite parameters");
			}
		}else {			
			if(testLevelParameterValue == null || testLevelParameterValue.equalsIgnoreCase(TestSuiteParameters.UNDEFINED)) { // if not defined in test level it will set as "UNDEFINED" or NULL		
				
				switch(parameter) {
				case TestSuiteParameters.BASE_URL : {
					returnParameterValue = String.valueOf(testConfig.baseUrl());
					break;
				}
				case TestSuiteParameters.IMPLICITLY_WAIT_TIME : {
					returnParameterValue = String.valueOf(testConfig.implicitlyWaitTime());							
					break;
				}
				case TestSuiteParameters.PAGE_LOAD_TIMEOUT : {
					returnParameterValue = String.valueOf(testConfig.pageLoadTimeout());
					break;
				}
				case TestSuiteParameters.SCRIPT_TIMEOUT : {
					returnParameterValue = String.valueOf(testConfig.scriptTimeout());
					break;
				}
				case TestSuiteParameters.BROWSER : {
					returnParameterValue = String.valueOf(testConfig.browser());
					break;
				}
			}
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from test configuration annotation ");
			}else {
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from test-level suite parameters");
			}
		}


		
		return returnParameterValue;
	}
	
	@Override
	public boolean hasTestConfigurationAnnotation(){		
		return getTestInstanceClass().isAnnotationPresent(TestConfiguration.class);
	}
	
	@Deprecated
	@Override
	public Object getTestInstance() {
		return result.getInstance(); // this works only result gets from AvtomatTestNGListner.onTestStart
	}
	
	@Override
	public Class<?> getTestInstanceClass() {
		try {
			return Class.forName(getTestClassName());
		} catch (ClassNotFoundException e) {
			throw new TestClassNotFoundException(getTestClassName() + " not found");		
		}
	}

	@Override
	public TestConfigurationContext getTestConfigurationContext() {
		if(null==testConfigurationContext) {
			getTestCofigurationFromScript();
		}
		return testConfigurationContext;
	}

	@Override
	public String getTestClassName() {
		return result.getTestClass().getName(); //result.getInstanceName(); also give the same result
	}
	
	// test name in suit 
	@Override
	public String getTestName() {	
		return result.getTestContext().getName();
	}

	@Override
	public String getTestPlanName() {
		return result.getTestContext().getSuite().getName();
	}

	@Override
	public String getTestPlanUUID() {		
		return result.getTestContext().getSuite().getAttribute(TestInitializr.TEST_PLAN_UUID).toString();
	}

	@Override
	public Testplan getTestPlanObj() {
		return (Testplan)result.getTestContext().getSuite().getAttribute(TestInitializr.TEST_PLAN_OBJ);
	}
	
	@Override
	public KTestConfig getTestConfigObj() {
		return (KTestConfig)result.getTestContext().getSuite().getAttribute(TestInitializr.TEST_CONFIG_OBJ);
	}
	
	@Override
	public SysConfig getSysConfigObj() {
		return (SysConfig)result.getTestContext().getSuite().getAttribute(TestInitializr.TEST_SYS_CONFIG_OBJ);
	}

	@Override
	public Map<String, String> getTestLevelParametersMap() {
		ObjectMapper oMapper = new ObjectMapper();
		return oMapper.convertValue(result.getAttribute(TestSuiteParameters.XML_TEST_LEVEL_MAP),Map.class);
	}

	@Override
	public Path getTestSuiteFilePath() {	
		return Paths.get(getTestPlanObj().getTestSuiteFileName());

	}

	@Override
	public String getKandyClientTestPlanId() {
		return result.getTestContext().getSuite().getAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID).toString();
	}

	@Override
	public String getKandyClientTestPlanAutomatedRunId() {
		return result.getTestContext().getSuite().getAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID).toString();
	}

	// this may need to handle test level currently tep header/suite level
	@Override
	public boolean isDryrun() {
		return (Boolean)result.getTestContext().getSuite().getAttribute(TestInitializr.IS_DRY_RUN);
	}

	@Override
	public TestNGConfig getTestNGConfig() {
		return (TestNGConfig)result.getTestContext().getSuite().getAttribute(TestInitializr.TESTNG_CONFIG_OBJ);
	}

}
