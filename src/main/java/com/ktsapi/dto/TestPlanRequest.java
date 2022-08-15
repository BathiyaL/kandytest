package com.ktsapi.dto;

public class TestPlanRequest {
	
	private String testPlanTemplateId; // need to pass ass TEP parameter
	private String testPlanRunId; // Auto generated id but convert to string with prefix e.g. TEP-119
	private String testPlanAutomatedRunId;
	private String testPlanRunName;
    private String initiatedTimestamp;
    private Integer totalTestCount;
    private Integer passedTestCount;
    private Integer failedTestCount;
    private Integer skippedTestCount;

    private String browser;
    private String baseURL;
    private String testPlanStatus; ///
	private String testPlanRunStatus;
    private String sutVersionNumber;



	private String testDriver;
    private String executionMode;

    private String modifiedTimestamp;
    private String executedBy; // manually renamed
    private String testSuiteFileName; // manually added
    private String applicationId;
    private Long workspaceId;
    
    private String executionStartTimestamp;
    

	private String executionCompletedTimestamp;

	public TestPlanRequest() {
    	
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
	
    public String getTestPlanAutomatedRunId() {
		return testPlanAutomatedRunId;
	}

	public void setTestPlanAutomatedRunId(String testPlanAutomatedRunId) {
		this.testPlanAutomatedRunId = testPlanAutomatedRunId;
	}
	
	public String getTestPlanTemplateId() {
		return testPlanTemplateId;
	}

	public void setTestPlanTemplateId(String testPlanTemplateId) {
		this.testPlanTemplateId = testPlanTemplateId;
	}
	
    public String getSutVersionNumber() {
		return sutVersionNumber;
	}

	public void setSutVersionNumber(String sutVersionNumber) {
		this.sutVersionNumber = sutVersionNumber;
	}
	
	public Long getWorkspaceId() {
		return workspaceId;
	}


	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}
	
    public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

    public String getTestPlanRunId() {
		return testPlanRunId;
	}
	public void setTestPlanRunId(String testPlanRunId) {
		this.testPlanRunId = testPlanRunId;
	}
	public String getTestPlanName() {
		return testPlanRunName;
	}
	public void setTestPlanName(String testPlanName) {
		this.testPlanRunName = testPlanName;
	}

	public Integer getTotalTestCount() {
		return totalTestCount;
	}
	public void setTotalTestCount(Integer totalTestCount) {
		this.totalTestCount = totalTestCount;
	}
	public Integer getPassedTestCount() {
		return passedTestCount;
	}
	public void setPassedTestCount(Integer passedTestCount) {
		this.passedTestCount = passedTestCount;
	}
	public Integer getFailedTestCount() {
		return failedTestCount;
	}
	public void setFailedTestCount(Integer failedTestCount) {
		this.failedTestCount = failedTestCount;
	}
	public Integer getSkippedTestCount() {
		return skippedTestCount;
	}
	public void setSkippedTestCount(Integer skippedTestCount) {
		this.skippedTestCount = skippedTestCount;
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
	public String getTestPlanStatus() {
		return testPlanStatus;
	}
	public void setTestPlanStatus(String testPlanStatus) {
		this.testPlanStatus = testPlanStatus;
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
	public String getModifiedTimestamp() {
		return modifiedTimestamp;
	}
	public void setModifiedTimestamp(String modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}
	public String getExecutedBy() {
		return executedBy;
	}
	public void setExecutedBy(String executedBy) {
		this.executedBy = executedBy;
	}
	public String getTestSuiteFileName() {
		return testSuiteFileName;
	}
	public void setTestSuiteFileName(String testSuiteFileName) {
		this.testSuiteFileName = testSuiteFileName;
	}
	
    public String getTestPlanRunStatus() {
		return testPlanRunStatus;
	}

	public void setTestPlanRunStatus(String testPlanRunStatus) {
		this.testPlanRunStatus = testPlanRunStatus;
	}

    
    
}
