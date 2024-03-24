package com.ktsapi.core;

public class TestRunner extends TestSession implements Runner{
	private TestContext testContext = null;

	public TestRunner(TestContext testContext) {
		super(); // initialize the TestCache through TestSession
		this.testContext = testContext;
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
		setTestConfigObj(testContext.getTestConfigObj());
		setSysConfigObj(testContext.getSysConfigObj());
		setDryRunStatus(testContext.isDryrun());
		setTestNGConfigObj(testContext.getTestNGConfig());
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
