package com.ktsapi.core;

import org.testng.ISuite;

import com.ktsapi.actions.core.ConfigLogger;

public class TestRunner extends TestSession implements Runner{
	private TestContext testContext = null;
	private TestSuiteContext testSuiteContext = null;
	
//	public TestRunner(TestSuiteContext testSuiteContext) {
//		super(); // initialize the TestCache through TestSession
//		this.testSuiteContext = testSuiteContext; // TODO:  suite information should pass to test cache
//		if(!isTestCacheInUse()) {
//			TestSession.open(testContext);
//			System.out.println("################################################################################## :1");
//		}
//	}
	
	public TestRunner(TestContext testContext) {
		super(); // initialize the TestCache through TestSession
		this.testContext = testContext;
//		System.out.println("########################TestRunner.setTestContextToTestCache()A : " + Thread.currentThread().getId() + " : "  +this.testContext.getTestConfigurationContext().getBrowser().name() );
//		System.out.println("########################TestRunner.setTestContextToTestCache()B : " + Thread.currentThread().getId() + " : "  +testContext.getTestConfigurationContext().getBrowser().name() );
//		System.out.println("################################################################################## :2");
	}
	
	@Override
	public void start() {
		if(!isTestCacheInUse()) {
			TestSession.open(testContext);
			setTestContextToTestCache();			
		}
	}
	
	/*
	 * set test level data to test cache
	 */
	private void setTestContextToTestCache() {
		setDesiredCapabilities(testContext.getDesiredCapabilities());
		setTestConfiguration(testContext.getTestConfigurationContext());
		
		
		
		// set initial wait times
		setImplicitlyWaitTime(testContext.getTestConfigurationContext().getImplicitlyWaitTime());
		setScriptTimeout(testContext.getTestConfigurationContext().getScriptTimeout());
		setPageLoadTimeout(testContext.getTestConfigurationContext().getPageLoadTimeout());
		
		setTestSuiteFilePath(testContext.getTestSuiteFilePath());
		setTestClassName(testContext.getTestClassName());
		setTestName(testContext.getTestName());
		setTestPlanName(testContext.getTestPlanName());
		setTestPlanUUID(testContext.getTestPlanUUID());
		setKandyClientTestPlanId(testContext.getKandyClientTestPlanId());
		setKandyClientTestPlanAutomatedRunId(testContext.getKandyClientTestPlanAutomatedRunId());
		
		setTestPlanObj(testContext.getTestPlanObj());
	}

	@Override
	public void end() {
		if(isTestCacheInUse()) {
			close();
		}
	}

	@Override
	public TestContext getTestContext() {		
		return testContext;
	}
}
