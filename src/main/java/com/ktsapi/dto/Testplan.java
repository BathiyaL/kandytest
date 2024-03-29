package com.ktsapi.dto;
import java.time.LocalDateTime;
import java.util.Date;

public class Testplan {


     private String testPlanUUID;
     private String kandyClientTestPlanId; // this need to get from TEP api call
	 private String testPlanName; // manually added
     private String testSuiteFileName; // manually added
     private Date intiatedDate;
     private LocalDateTime executionStartTimestamp;
     private LocalDateTime executionCompletedTimestamp;
     private Date completedDate;
     private String executedBy; // manually renamed
     private Integer passedCount;
     private Integer failedCount;
     private Integer skippedCount;
     
     private Integer totalTestsInTEPXml;
     
     private String parm_sutVersionNumber;

     private String parm_baseUrl;     
     private boolean parm_overrideTestParameters;
     private String parm_implicitlyWaitTime;
     private String parm_pageLoadTimeout;
     private String parm_scriptTimeout;
     private String browser;
     private String testDriver;
     private String executionMode;
     
    private String testPlanTemplateId; 
	private String testPlanRunId;
	
	public Testplan() {
    }

	
    public Testplan(String testPlanid) {
        this.testPlanUUID = testPlanid;
    }
    public Testplan(String testPlanid, Date intiatedDate, Date completedDate, String executor, Integer passedCount, Integer failedCount, Integer skippedCount) {
       this.testPlanUUID = testPlanid;
       this.intiatedDate = intiatedDate;
       this.completedDate = completedDate;
       this.executedBy = executor;
       this.passedCount = passedCount;
       this.failedCount = failedCount;
       this.skippedCount = skippedCount;
    }
    
	public String getTestPlanRunId() {
		return testPlanRunId;
	}

	public void setTestPlanRunId(String testPlanRunId) {
		this.testPlanRunId = testPlanRunId;
	}
	
    public String getTestPlanTemplateId() {
		return testPlanTemplateId;
	}

	public void setTestPlanTemplateId(String testPlanTemplateId) {
		this.testPlanTemplateId = testPlanTemplateId;
	}

    public String getParm_sutVersionNumber() {
		return parm_sutVersionNumber;
	}

	public void setParm_sutVersionNumber(String parm_sutVersionNumber) {
		this.parm_sutVersionNumber = parm_sutVersionNumber;
	}
    public String getKandyClientTestPlanId() {
		return kandyClientTestPlanId;
	}

	public void setKandyClientTestPlanId(String kandyClientTestPlanId) {
		this.kandyClientTestPlanId = kandyClientTestPlanId;
	}
    
	public String getExecutionMode() {
		return executionMode;
	}


	public void setExecutionMode(String executionMode) {
		this.executionMode = executionMode;
	}
    
    public String getTestDriver() {
		return testDriver;
	}


	public void setTestDriver(String testDriver) {
		this.testDriver = testDriver;
	}

    public String gettestPlanID() {
        return this.testPlanUUID;
    }
    
    public void settestPlanID(String testPlanid) {
        this.testPlanUUID = testPlanid;
    }

    public String getTestPlanName() {
        return this.testPlanName;
    }
    
    public void setTestPlanName(String testPlanName) {
        this.testPlanName = testPlanName;
    }

    public Date getIntiatedDate() {
        return this.intiatedDate;
    }
    
    public void setIntiatedDate(Date intiatedDate) {
        this.intiatedDate = intiatedDate;
    }

    public LocalDateTime getExecutionStartTimestamp() {
        return this.executionStartTimestamp;
    }
    
    public void setExecutionStartTimestamp(LocalDateTime executionStartTimestamp) {
        this.executionStartTimestamp = executionStartTimestamp;
    }

    public LocalDateTime getExecutionCompletedTimestamp() {
        return this.executionCompletedTimestamp;
    }
    
    public void setExecutionCompletedTimestamp(LocalDateTime executionCompletedTimestamp) {
        this.executionCompletedTimestamp = executionCompletedTimestamp;
    }

    public Date getCompletedDate() {
        return this.completedDate;
    }
    
    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getExecutedBy() {
        return this.executedBy;
    }
    
    public void setExecutedBy(String executor) {
        this.executedBy = executor;
    }

    public Integer getPassedCount() {
        return this.passedCount;
    }
    
    public void setPassedCount(Integer passedCount) {
        this.passedCount = passedCount;
    }

    public Integer getFailedCount() {
        return this.failedCount;
    }
    
    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }

    public Integer getSkippedCount() {
        return this.skippedCount;
    }
    
    public void setSkippedCount(Integer skippedCount) {
        this.skippedCount = skippedCount;
    }

    public Integer getTotalTestsInTEPXml() {
        return this.totalTestsInTEPXml;
    }
    
    public void setTotalTestsInTEPXml(Integer totalTestsInTEPXml) {
        this.totalTestsInTEPXml = totalTestsInTEPXml;
    }


    public String getParm_baseUrl() {
		return parm_baseUrl;
	}


    public void setParm_baseUrl(String baseUrl) {
		this.parm_baseUrl = baseUrl;
	}


    public boolean getParm_overrideTestParameters() {
		return parm_overrideTestParameters;
	}


    public void setParm_overrideTestParameters(boolean parm_overrideTestParameters) {
		this.parm_overrideTestParameters = parm_overrideTestParameters;
	}


    public String getParm_implicitlyWaitTime() {
		return parm_implicitlyWaitTime;
	}


    public void setParm_implicitlyWaitTime(String parm_implicitlyWaitTime) {
		this.parm_implicitlyWaitTime = parm_implicitlyWaitTime;
	}


    public String getParm_pageLoadTimeout() {
		return parm_pageLoadTimeout;
	}


    public void setParm_pageLoadTimeout(String parm_pageLoadTimeout) {
		this.parm_pageLoadTimeout = parm_pageLoadTimeout;
	}


    public String getParm_scriptTimeout() {
		return parm_scriptTimeout;
	}


    public void setParm_scriptTimeout(String parm_scriptTimeout) {
		this.parm_scriptTimeout = parm_scriptTimeout;
	}


	public String getBrowser() {
		return browser;
	}


	public void setBrowser(String browser) {
		this.browser = browser;
	}


	public String getTestSuiteFileName() {
		return testSuiteFileName;
	}


	public void setTestSuiteFileName(String testSuiteFileName) {
		this.testSuiteFileName = testSuiteFileName;
	}




}


