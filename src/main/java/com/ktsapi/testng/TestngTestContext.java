package com.ktsapi.testng;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
//import org.hibernate.dialect.FrontBaseDialect;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.contexts.TestConfigurationContext;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.TestContext;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.dto.Testplan;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.TestClassNotFoundException;

public class TestngTestContext implements TestContext{
	
//	private static final Logger LOG = Logger.getLogger("CONFIG");
	
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
		chromeOptions = testConfig.chromeOptions();
		executionMode = testConfig.executionMode();
		gridHubURL=testConfig.gridHubURL();
		browserversion = testConfig.browserVersion();
		chromeOptions = testConfig.chromeOptions();
		

		implicitlyWaitTime=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.IMPLICITLY_WAIT_TIME, testPlan,testConfig)); //testConfig.implicitlyWaitTime();
		scriptTimeout=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.SCRIPT_TIMEOUT, testPlan,testConfig));
		pageLoadTimeout=Long.parseLong(getValueOfStandardParameter(TestSuiteParameters.PAGE_LOAD_TIMEOUT, testPlan,testConfig));
		
		testConfigurationContext = new TestConfigurationContext();
		testConfigurationContext.setBaseUrl(baseUrl);		
		testConfigurationContext.setBrowser(browser);
		
		testConfigurationContext.setTestDriver(testDriver);
		testConfigurationContext.setBrowserVersion(browserversion);
		testConfigurationContext.setChromeOptions(chromeOptions);
		testConfigurationContext.setExecutionMode(executionMode);
		testConfigurationContext.setGridHubURL(gridHubURL);
		testConfigurationContext.setImplicitlyWaitTime(implicitlyWaitTime);
		testConfigurationContext.setScriptTimeout(scriptTimeout);
		testConfigurationContext.setPageLoadTimeout(pageLoadTimeout);
	}
	
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
		
		if (suiteLevelParameterValue != null && suiteLevelParameterValue!="UNDEFINED") { // if not defined in suite level it will set as "UNDEFINED"
			if (!suiteLevelParameterValue.equals(testLevelParameterValue)) {
				/*
				 * different means it gives priority to test level parameter
				 */
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from test-level suite parameters");
			} else {
				ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + " from suite parameters");
			}
		}else {			
			if(testLevelParameterValue == null) {				
				
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
	
	
	private String getValueOfStandardParameter3(String parameter,Testplan testPlan , TestConfiguration testConfig) {
				
		String suiteLevelParameterValue=null;
		String testLevelParameterValue = getTestLevelParametersMap().get(parameter);
		String logPostfix = " from test-level suite parameter";
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
//		case TestSuiteParameters.PAGE_LOAD_TIMEOUT : {
//			parameterValue = testPlan.getParm_implicitlyWaitTime();
//			break;
//		}
	}
		
		
		if(suiteLevelParameterValue!=null) {			
			if(testLevelParameterValue!=null) {		
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>1  :: " + returnParameterValue);
				if(testPlan.getParm_overrideTestParameters()) {
					returnParameterValue = suiteLevelParameterValue;
					
					logPostfix = " from header-level suite parameter";
				}
			}else {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>  :: " + returnParameterValue);
				switch(parameter) {
					case TestSuiteParameters.BASE_URL : {
						returnParameterValue = testPlan.getParm_baseUrl();
						break;
					}
					case TestSuiteParameters.IMPLICITLY_WAIT_TIME : {
						returnParameterValue = testPlan.getParm_implicitlyWaitTime();
						
						break;
					}
				}				
				logPostfix = " from header-level suite parameter";
			}
		}else {
			if(getTestLevelParametersMap().get(parameter)==null) {
				switch(parameter) {
					case TestSuiteParameters.BASE_URL : {
						returnParameterValue = testConfig.baseUrl();
						break;
					}
					case TestSuiteParameters.IMPLICITLY_WAIT_TIME : {
						returnParameterValue = String.valueOf(testConfig.implicitlyWaitTime());
						break;
					}
				}				
				logPostfix = " from test configuration annotation ";
			}			
		}
		
//		// validate numeric values
//		switch(parameter) {
//			case TestSuiteParameters.IMPLICITLY_WAIT_TIME : {
//				try {
//					Long.parseLong(parameterValue);
//				}catch(NumberFormatException e) {
//					ConfigLogger.logWarn(parameter + " value \""+parameterValue + "\" defined in test suite xml is not a valid numeric value.");
//					parameterValue = String.valueOf(testConfig.implicitlyWaitTime());
//					logPostfix = " from test configuration annotation ";
//					
//				}
//				break;
//			}
//		}	
		
		if(StringUtils.isEmpty(returnParameterValue)) {
			ConfigLogger.logWarn(parameter + " not defined in either test suite or test configuration");
		}else {
			ConfigLogger.logInfo("Setting " + parameter + " " + returnParameterValue + logPostfix);
		}
		
		
		
		
		return returnParameterValue;
	}
	
//	private String getBaseUrlFromParameters(Testplan testPlan , TestConfiguration testConfig) {
//		String logPostfix = " from test-level suite parameter";
//		String baseURL = getTestLevelParametersMap().get(TestSuiteParameters.BASE_URL);
//		if(testPlan.getParm_baseUrl()!=null) {			
//			if(getTestLevelParametersMap().get(TestSuiteParameters.BASE_URL)!=null) {				
//				if(testPlan.getParm_overrideTestParameters()) {
//					baseURL = testPlan.getParm_baseUrl();
//					logPostfix = " from header-level suite parameter";
//				}
//			}else {
//				baseURL = testPlan.getParm_baseUrl();
//				logPostfix = " from header-level suite parameter";
//			}
//		}else {
//			if(getTestLevelParametersMap().get(TestSuiteParameters.BASE_URL)==null) {
//				baseURL = testConfig.baseUrl();	
//				logPostfix = " from test configuration annotation ";
//			}			
//		}
//		
//		if(StringUtils.isEmpty(baseURL)) {
//			ConfigLogger.logWarn("Base url not defined in either test suite or test configuration");
//		}else {
//			ConfigLogger.logInfo("Setting base url " + baseURL + logPostfix);
//		}
//		
//		return baseURL;
//	}
	
	
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



//	@Override
//	public Browsers getTestConfigurationBrowser() {
//		return this.browser;
//	}
//
//	@Override
//	public String[] getTestConfigurationChromeOptions() {
//		return this.chromeOptions;
//	}

}
