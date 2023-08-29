package com.ktsapi.testng;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.testng.ISuite;
import org.testng.ITestContext;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.TestSuiteValidationException;

public class TestSuiteValidator {

	private ISuite suite;
	private ITestContext testContext;
	public TestSuiteValidator(ISuite suite) {
		this.suite = suite;
		this.testContext=null;
	}
	public TestSuiteValidator(ITestContext testContext) {
		this.testContext=testContext;
		this.suite = null;
	}
	
	public String getBrowserParameterValue(){
		
		if(suite!=null) {
			return suite.getXmlSuite().getParameter(TestSuiteParameters.BROWSER);
		}
		if(testContext!=null) {
			return testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.BROWSER);
		}
		return null;
	}
	
	public String validateAndGetBrowserParameterValue() {
		String browserParmValue = getBrowserParameterValue();
		// TODO : Write generic validation, to validate all values of Browser enum instead one by one
		if(browserParmValue!=null) {
			
			try {
				Browsers.valueOf(browserParmValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"browser\" parameter. Value Should be one of " + Arrays.asList(Browsers.values()) ; // get list form Browser enum
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return Browsers.UNDEFINED.name();
		}
		
		return browserParmValue.toUpperCase();
	}
	
	public String validateExecutionModeParameterValue() {
		String executionModeValue = suite.getXmlSuite().getParameter(TestSuiteParameters.EXECUTION_MODE);
		if(executionModeValue!=null) {
			
			try {
				ExecutionMode.valueOf(executionModeValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"executionMode\" parameter. Value Should be one of " + Arrays.asList(TestDriver.values()) ;
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return ExecutionMode.UNDEFINED.name();
		}
		
		return executionModeValue.toUpperCase();
	}
	
	public String validateTestDriverParameterValue() {
		String testDriverValue = suite.getXmlSuite().getParameter(TestSuiteParameters.TEST_DRIVER);
		if(testDriverValue!=null) {
			
			try {
				TestDriver.valueOf(testDriverValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"testDriver\" parameter. Value Should be one of " + Arrays.asList(TestDriver.values()) ;
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return TestDriver.UNDEFINED.name();
		}
		
		return testDriverValue.toUpperCase();
	}
	
	public String validateBaseUrlParameterValue() {
		String baseUrl = suite.getXmlSuite().getParameter(TestSuiteParameters.BASE_URL);
		// TODO :: validate for valid url format
		if(baseUrl==null) {
			return "UNDEFINED";
		}
		
		return baseUrl;
	}
	
	public String validateNumericParameterValueOf(String parameter) {	
		String parameterValue = suite.getXmlSuite().getParameter(parameter);
	
		try {
			if(parameterValue!=null) {
				Long.parseLong(parameterValue);
			}
		} catch (NumberFormatException e) {
			String errMsg = suite.getXmlSuite().getFileName() + " contains invalid numieric value \""+parameterValue+"\" for " + parameter + " paramter. Correct it and re-run";
			ConfigLogger.logError(errMsg);
			throw new TestSuiteValidationException(errMsg);
		}
		
		return parameterValue;
	}
	
	public boolean validateOverrideTestParameterValue() {
	     String overrideTestParameters = suite.getXmlSuite().getParameter(TestSuiteParameters.OVERRIDE_TEST_PARAMETERS);
	     
	     if(overrideTestParameters==null) {
	    	 return Boolean.parseBoolean("false"); // Not define in suite or no run on default suite	    	 
	     }else {
	    	 if(StringUtils.equalsIgnoreCase(overrideTestParameters, "true") || StringUtils.equalsIgnoreCase(overrideTestParameters, "false")) {
	    		 return Boolean.parseBoolean(StringUtils.lowerCase(overrideTestParameters));
	    	 }else {
	    		 String errMsg = "Invalid value for \""+TestSuiteParameters.OVERRIDE_TEST_PARAMETERS+"\" parameter. Value Should be one of true for false";
	    		 throw new TestSuiteValidationException(errMsg);
	    	 }
	    	 
	     }
	}
}
