package com.ktsapi.core;

import java.nio.file.Path;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.contexts.TestConfigurationContext;
import com.ktsapi.dto.Testplan;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.testng.KTestConfig;

public interface TestContext {

	/**
	 * Get Test capabilities from initial configuration
	 * @return DesiredCapabilities
	 */
	DesiredCapabilities getDesiredCapabilities();
//	TestDriver getTestDriver();
	Object getTestInstance();
	boolean hasTestConfigurationAnnotation();
	
	TestConfigurationContext getTestConfigurationContext();
	Class<?> getTestInstanceClass();
	
	Path getTestSuiteFilePath();
	String getTestPlanName();
	String getTestClassName();

	String getTestPlanUUID();
	String getKandyClientTestPlanId();
	String getKandyClientTestPlanAutomatedRunId();
	String getTestName();
	
	Testplan getTestPlanObj();
	KTestConfig getTestConfigObj();
	
	boolean isDryrun();

	
	Map<String,String> getTestLevelParametersMap();
	
//	Browsers getTestConfigurationBrowser();
//	String[] getTestConfigurationChromeOptions();
	
}
