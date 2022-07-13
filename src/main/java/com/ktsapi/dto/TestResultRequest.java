package com.ktsapi.dto;

public class TestResultRequest {
	
	private String testPlanId;
    private String executionStartTimestamp;
    private String executionCompletedTimestamp;
    private String testResultStatus;
    private String browser;
    private String baseURL;
    private String testDriver;
    private String executionMode;
    private String testName;
    private String testClass;
	private String testExecutionLog;
	private String executionNode;
	private String testCaseId;  
	private String testPlanAutomatedRunId;


	public TestResultRequest() {
    	
    }
	
	public String getTestPlanAutomatedRunId() {
		return testPlanAutomatedRunId;
	}

	public void setTestPlanAutomatedRunId(String testPlanAutomatedRunId) {
		this.testPlanAutomatedRunId = testPlanAutomatedRunId;
	}
	
	public String getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	
    public String getExecutionNode() {
		return executionNode;
	}

	public void setExecutionNode(String executionNode) {
		this.executionNode = executionNode;
	}
    
	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}    
    public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}    
    public String getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}
	public String getExecutionStartTimestamp() {
		return executionStartTimestamp;
	}
	public void setExecutionStartTimestamp(String executionStartTimestamp) {
		this.executionStartTimestamp = executionStartTimestamp;
	}
	public String getExecutionCompletedTimestamp() {
		return executionCompletedTimestamp;
	}
	public void setExecutionCompletedTimestamp(String executionCompletedTimestamp) {
		this.executionCompletedTimestamp = executionCompletedTimestamp;
	}
	public String getTestResultStatus() {
		return testResultStatus;
	}
	public void setTestResultStatus(String testResultStatus) {
		this.testResultStatus = testResultStatus;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	public String getTestDriver() {
		return testDriver;
	}
	public void setTestDriver(String testDriver) {
		this.testDriver = testDriver;
	}
	public String getExecutionMode() {
		return executionMode;
	}
	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
	}
	public String getTestExecutionLog() {
		return testExecutionLog;
	}
	public void setTestExecutionLog(String testExecutionLog) {
		this.testExecutionLog = testExecutionLog;
	}

    
    
    
    
}
