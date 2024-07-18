package com.ktsapi.testng;

import java.util.Map;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.TestInitializr;

public class TestNgUtil {
	public static final String testNameDelimeter = ":";
	public static final String testIDPrefix = "TC-";
	private ITestResult result;
	private String testID;
	private String testName;
	
	public TestNgUtil(ITestResult result) {
		this.result = result;
		setTestNameAndId();
	}
	
	private void setTestNameAndId() {
		if(isOneToOneMap()) {
			Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
			if(testAnnotation!=null) {
				String[] testNameArray = testAnnotation.testName().split(testNameDelimeter);
				if(testNameArray.length==2) {
					testID = testNameArray[0];
					testName = testNameArray[1];
				} else {
					testName =testAnnotation.testName();
				}
			}
		} else {
			// getting testid from TEP is there better way to handle this
			String[] nampeSplit = result.getTestContext().getName().split("\\.");
			String testClassName = nampeSplit[nampeSplit.length - 1];
			if (testClassName.contains(testNameDelimeter) && testClassName.contains("TC")) {
				testID = "TC-" + (testClassName.split(testNameDelimeter)[0].split("TC"))[1];
				testName = testClassName.split(testNameDelimeter)[1];
			}
		}
	}

	public String getTestName() {
		return testName;
	}
	
	public String getTestID() {
		return testID;
	}
	
	public boolean isOneToOneMap() {
		boolean headerParm = (Boolean)result.getTestContext().getSuite().getAttribute(TestInitializr.IS_ONE_TO_ONE_MAPPING);
		if(headerParm) return headerParm; // give priority to header level parameter
		@SuppressWarnings("unchecked")
		String testLevelParm = ((Map<String, String>) result.getAttribute(TestSuiteParameters.XML_TEST_LEVEL_MAP)).get(TestSuiteParameters.IS_ONE_T0_ONE_MAPPING);
		if(testLevelParm!=null) return Boolean.parseBoolean(testLevelParm);  
		return false;

	}
}
