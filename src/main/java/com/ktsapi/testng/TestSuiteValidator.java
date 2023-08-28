package com.ktsapi.testng;

import java.util.Arrays;

import org.testng.ISuite;
import org.testng.ITestContext;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.enums.Browsers;
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
}
