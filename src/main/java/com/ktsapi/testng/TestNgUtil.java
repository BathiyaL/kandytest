package com.ktsapi.testng;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.TestInitializr;

public class TestNgUtil {
	public static final String testNameDelimeter = ":";
	public static final String testIDPrefix = "TC-";

	public static String getTestName(ITestResult result) {
		String testName = "";
		if(isOneToOneMap(result)) {
			Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
			if(testAnnotation!=null) {
				String[] testNameArray = testAnnotation.testName().split(testNameDelimeter);
				if(testNameArray.length==2) {
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
				testName = testClassName.split(testNameDelimeter)[1];
			}
		}
		return testName;
	}
	
	public static String getTestID(ITestResult result) {
		String testID = "";
		if(isOneToOneMap(result)) {
			Test testAnnotation = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
			if(testAnnotation!=null) {
				String[] testNameArray = testAnnotation.testName().split(testNameDelimeter);
				if(testNameArray.length==2) {
					testID = testNameArray[0];
				}
			}
		} else {
			// getting testid from TEP is there better way to handle this
			String[] nampeSplit = result.getTestContext().getName().split("\\.");
			String testClassName = nampeSplit[nampeSplit.length - 1];
			if (testClassName.contains(testNameDelimeter) && testClassName.contains("TC")) {
				testID = "TC-" + (testClassName.split(testNameDelimeter)[0].split("TC"))[1];
			}
		}
		return testID;
	}
	
	public static boolean isOneToOneMap(ITestResult result) {
		
		boolean headerParm = (Boolean)result.getTestContext().getSuite().getAttribute(TestInitializr.IS_ONE_TO_ONE_MAPPING);
		if(headerParm) return headerParm; // give priority to header level parameter
		
		@SuppressWarnings("unchecked")
		String testLevelParm = ((Map<String, String>) result.getAttribute(TestSuiteParameters.XML_TEST_LEVEL_MAP)).get(TestSuiteParameters.IS_ONE_T0_ONE_MAPPING);

		if(testLevelParm!=null) return Boolean.parseBoolean(testLevelParm);  
		
		return false;

	}
}
