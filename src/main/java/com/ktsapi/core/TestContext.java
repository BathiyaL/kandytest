package com.ktsapi.core;

import java.nio.file.Path;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.ktsapi.contexts.TestConfigurationContext;
import com.ktsapi.dto.Testplan;
import com.ktsapi.testng.TestNGConfig;
import com.ktsapi.utils.sysconfig.SysConfig;
import com.ktsapi.utils.testconfig.KTestConfig;

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
	String getTestID();
	
	Testplan getTestPlanObj();
	KTestConfig getTestConfigObj();
	SysConfig getSysConfigObj();
	TestNGConfig getTestNGConfig();

	boolean isDryrun();
	boolean isOneToOneMap();

	
	Map<String,String> getTestLevelParametersMap();
	
//	Browsers getTestConfigurationBrowser();
//	String[] getTestConfigurationChromeOptions();
	
}
