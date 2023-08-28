package com.ktsapi.testng;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.dto.TestPlanRequest;
import com.ktsapi.dto.Testplan;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.TestSuiteValidationException;
import com.ktsapi.kclient.KandyClientApiCaller;
import com.ktsapi.utils.AvtomatUtils;
import com.ktsapi.utils.sysconfig.SysConfig;
import com.ktsapi.utils.testconfig.KTestConfig;

public class AvtomatSuiteListner implements ISuiteListener  {
	private Integer testCount = 0 ; 
	private Testplan testPlan = null;
	private List<ITestNGMethod> testMethods = null;
	private String kandyTestPlanID;
	private String kandyTestPlanAutmatedRunID;
	private boolean isDryRun;
	private Long workspaceId = 101L;

	 public void setTestCount(int testCount){
	      this.testCount = testCount;
	 }

	 public long getTestCount(){
	     return this.testCount;
	 }
	 
	@Override
	public void onStart(ISuite suite) {
		
		KTestConfig ktestConfig = AvtomatUtils.validateAndGetKTestConfig();
		SysConfig sysConfig = AvtomatUtils.validateAndGetSysConfig();
		TestSuiteValidator testSuiteValidator = new TestSuiteValidator(suite);

		suite.setAttribute(TestInitializr.TEST_CONFIG_OBJ, ktestConfig);
		suite.setAttribute(TestInitializr.TEST_SYS_CONFIG_OBJ, sysConfig);  
		
		isDryRun = getIsDryRunForTestInstance(ktestConfig, suite);

		 testMethods  = suite.getAllMethods();
	     this.testCount = testMethods.size();
	     
	     // init TestPlan object
	     String testPlanUUID = AvtomatUtils.getUUID();
	     
	    // TODO : we may set these things to initial starter
		System.setProperty("test.suite.uuid", testPlanUUID);
			
		 try {
			 LoggerContext context  = (LoggerContext)LogManager.getContext(false);
			context.setConfigLocation(AvtomatSuiteListner.class.getResource("/log4j2.properties").toURI());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     	     
	     testPlan = new Testplan(testPlanUUID);
	     testPlan.setExecutionStartTimestamp(AvtomatUtils.getCurretnTimeStamp());
	     testPlan.setTestPlanName(suite.getName());
	     testPlan.setTestSuiteFileName(suite.getXmlSuite().getFileName());
	     testPlan.setExecutedBy(AvtomatUtils.getWindowsLoggedInUser());
	     testPlan.setTotalTestsInTEPXml(this.testCount);
	     
	     testPlan.setParm_sutVersionNumber(validateNumericParameterValueOf(TestSuiteParameters.SUT_VERSION_NUMBER,suite));
	     testPlan.setParm_implicitlyWaitTime(validateNumericParameterValueOf(TestSuiteParameters.IMPLICITLY_WAIT_TIME,suite));
	     testPlan.setParm_scriptTimeout(validateNumericParameterValueOf(TestSuiteParameters.SCRIPT_TIMEOUT,suite));
	     testPlan.setParm_pageLoadTimeout(validateNumericParameterValueOf(TestSuiteParameters.PAGE_LOAD_TIMEOUT,suite));	     
	     
	     //testPlan.setBrowser(validateBrowserParameterValue(suite));
	     testPlan.setBrowser(testSuiteValidator.validateAndGetBrowserParameterValue());
	     testPlan.setParm_baseUrl(validateBaseUrlParameterValue(suite));
	     testPlan.setTestDriver(validateTestDriverParameterValue(suite));
	     testPlan.setExecutionMode(validateExecutionModeParameterValue(suite));
	     
	     testPlan.setTestPlanRunId(validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_RUN_ID,suite));
	     testPlan.setTestPlanTemplateId(validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_TEMPLATE_ID,suite));
	     
	     
	     String overrideTestParameters = suite.getXmlSuite().getParameter(TestSuiteParameters.OVERRIDE_TEST_PARAMETERS);
	     
	     if(overrideTestParameters==null) {
	    	 testPlan.setParm_overrideTestParameters(Boolean.parseBoolean("false"));; // Not define in suite or no run on default suite	    	 
	     }else {
	    	 if(StringUtils.equalsIgnoreCase(overrideTestParameters, "true") || StringUtils.equalsIgnoreCase(overrideTestParameters, "false")) {
	    		 testPlan.setParm_overrideTestParameters(Boolean.parseBoolean(StringUtils.lowerCase(overrideTestParameters)));
	    	 }else {
	    		 // TODO : should fail the script since invalid parameters
	    	 }
	    	 
	     }
	    
	     ConfigLogger.logInfo("@TestSuite{"+suite.getXmlSuite().getFileName()+"} is starting.....");
	        
	     suite.setAttribute(TestInitializr.TEST_EXECUTED_BY, AvtomatUtils.getWindowsLoggedInUser());   
	     suite.setAttribute(TestInitializr.TEST_PLAN_UUID, AvtomatUtils.getUUID());
	     suite.setAttribute(TestInitializr.TOTOAL_TESTS_IN_TEST_PLAN_XML, this.testCount);
	     suite.setAttribute(TestInitializr.TEST_PLAN_OBJ, testPlan);
	     suite.setAttribute(TestInitializr.IS_DRY_RUN, isDryRun);
	     
	     handleKandyClientAPI(suite,getTestPlanRequestForKandyclient(ktestConfig));

	}
	
	private TestPlanRequest getTestPlanRequestForKandyclient(KTestConfig ktestConfig) {
	    String tepStartTimestamp = AvtomatUtils.localDateTimeStringFormat(testPlan.getExecutionStartTimestamp());

		TestPlanRequest testPlanRequest = new TestPlanRequest();
		testPlanRequest.setTestPlanName(testPlan.getTestPlanName());
		testPlanRequest.setExecutionStartTimestamp(tepStartTimestamp);
		testPlanRequest.setTotalTestCount(testPlan.getTotalTestsInTEPXml());
		testPlanRequest.setBrowser(testPlan.getBrowser());
		testPlanRequest.setBaseURL(testPlan.getParm_baseUrl());
		testPlanRequest.setTestDriver(testPlan.getTestDriver());
		testPlanRequest.setExecutionMode(testPlan.getExecutionMode());
		testPlanRequest.setExecutedBy(testPlan.getExecutedBy());
		testPlanRequest.setTestSuiteFileName(testPlan.getTestSuiteFileName());
		testPlanRequest.setApplicationId(ktestConfig.getApplicationId());
		testPlanRequest.setWorkspaceId(workspaceId);
		testPlanRequest.setModifiedTimestamp(tepStartTimestamp);
		
		testPlanRequest.setTestPlanTemplateId(testPlan.getTestPlanTemplateId());
		testPlanRequest.setTestPlanRunId(testPlan.getTestPlanRunId());
		
		// for TestPlan run endpoint
		testPlanRequest.setInitiatedTimestamp(testPlanRequest.getExecutionStartTimestamp());
		testPlanRequest.setTotalTestCount(3);
		testPlanRequest.setPassedTestCount(0);
		testPlanRequest.setFailedTestCount(0);
		testPlanRequest.setFailedTestCount(0);
		
		return testPlanRequest;
	}
	
	private void handleKandyClientAPI(ISuite suite, TestPlanRequest testPlanRequest) {
		if(!isDryRun) {
			try {
				TestPlanRequest testPlanAutomatedRunResponse=null;
				
				// TODO : handle use case when runid is not provided , to initiate new Run
				// 	TODO : when run id is provided , no use of template id,
				// Logic : if both the ids are provided we can give priority to the the run id	

				if(isTestPlanRunIdIsProvided(testPlan.getTestPlanRunId())) {
					testPlanAutomatedRunResponse = KandyClientApiCaller.postTestPlanAutomatedRun(testPlanRequest);
				} else if(isTestPlanTemplateIdProvided(testPlan.getTestPlanTemplateId())) {
					
					// TODO : instead for invoking two endpoints from here we can come up with single endpoint to handle from client backend
					TestPlanRequest testPlanRunResponse = KandyClientApiCaller.postTestPlanRun(testPlanRequest);
					if(testPlanRunResponse != null) {
						testPlanRequest.setTestPlanRunId(testPlanRunResponse.getTestPlanRunId());
						testPlanAutomatedRunResponse = KandyClientApiCaller.postTestPlanAutomatedRun(testPlanRequest);
					}
 					
				} else {
					ConfigLogger.logInfo("@TestSuite{ TestPlanTemplateId or TestPlanTemplateId parameters are not provided }");
				}
				
				
				if(testPlanAutomatedRunResponse!=null ) {
					if(testPlanAutomatedRunResponse.getTestPlanRunId()!=null && !testPlanAutomatedRunResponse.getTestPlanRunId().equals("")) {						
						kandyTestPlanID = testPlanAutomatedRunResponse.getTestPlanRunId();						
						kandyTestPlanAutmatedRunID = testPlanAutomatedRunResponse.getTestPlanAutomatedRunId();
						ConfigLogger.logInfo("@TestSuite{ [KandyClient] => TestAutomatedRunId-"+ kandyTestPlanAutmatedRunID +" initiated under Test Plan "+ kandyTestPlanID + "..........}");
						suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID, kandyTestPlanAutmatedRunID); 
						suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, kandyTestPlanID); 
						
					}else {
						suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID, "UNDEFINED");
						suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, "UNDEFINED");
					}
				}else {
					suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID, "UNDEFINED");
					suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, "UNDEFINED");
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			ConfigLogger.logInfo("@TestSuite{DryRun=True}"); 
			suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, "DRY_RUN");  
			suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_AUTOMATED_RUN_ID, "DRY_RUN"); 
		}
	}
	
	private boolean isTestPlanTemplateIdProvided(String testPlanTemplateId) {
		if(testPlanTemplateId==null || testPlanTemplateId.isEmpty()) {
			return false;
		}
		// is numeric is check before come to this point
		//we can do further validation if required.
		
		return true;
	}
	
	private boolean isTestPlanRunIdIsProvided(String testPlanRunId) {
		if(testPlanRunId==null || testPlanRunId.isEmpty()) {
			return false;
		}
		// is numeric is check before come to this point
		//we can do further validation if required.
		
		return true;
	}
	
	/*
	 * Handle the isDryRun logic and return isDryrun status for the test execution
	 * if isDryRun==true , then test result should not persist to the db
	 */
	private Boolean getIsDryRunForTestInstance(KTestConfig ktestConfig, ISuite suite) {
		boolean tepParameterValue = Boolean.parseBoolean(suite.getXmlSuite().getParameter(TestSuiteParameters.IS_DRY_RUN)); // DOC BL : if not define in TEP return default value true
		boolean appConfigValue = ktestConfig.getIsDryRun();
		
		if(appConfigValue) {
			return true;
		}else {
			return tepParameterValue ;
		}
	} 
	
	
	private String validateNumericParameterValueOf(String parameter,ISuite suite) {	
		String parameterValue = suite.getXmlSuite().getParameter(parameter);
	
		try {
			if(parameterValue!=null) {
				Long.parseLong(parameterValue);
			}
		} catch (NumberFormatException e) {
			String errMsg = suite.getXmlSuite().getFileName() + " contains invalid numieric value \""+parameterValue+"\" for " + parameter + " paramter. Correct it and re-run";
			ConfigLogger.logError(errMsg);
			throw new TestSuiteValidationException(errMsg);
		}
		
		return parameterValue;
	}
	
	private String validateExecutionModeParameterValue(ISuite suite) {
		String executionModeValue = suite.getXmlSuite().getParameter(TestSuiteParameters.EXECUTION_MODE);
		if(executionModeValue!=null) {
			
			try {
				ExecutionMode.valueOf(executionModeValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"executionMode\" parameter. Value Should be one of " + Arrays.asList(TestDriver.values()) ;
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return ExecutionMode.UNDEFINED.name();
		}
		
		return executionModeValue.toUpperCase();
	}
	
	private String validateTestDriverParameterValue(ISuite suite) {
		String testDriverValue = suite.getXmlSuite().getParameter(TestSuiteParameters.TEST_DRIVER);
		if(testDriverValue!=null) {
			
			try {
				TestDriver.valueOf(testDriverValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"testDriver\" parameter. Value Should be one of " + Arrays.asList(TestDriver.values()) ;
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return TestDriver.UNDEFINED.name();
		}
		
		return testDriverValue.toUpperCase();
	}
	
	private String validateBaseUrlParameterValue(ISuite suite) {
		String baseUrl = suite.getXmlSuite().getParameter(TestSuiteParameters.BASE_URL);
		// TODO :: validate for valid url format
		if(baseUrl==null) {
			return "UNDEFINED";
		}
		
		return baseUrl;
	}

	@Override
	public void onFinish(ISuite suite) {
		long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);		
		
		ConfigLogger.logInfo("@TestSuite{"+suite.getXmlSuite().getFileName()+"} execution has completed, took around " + seconds + " seconds"); 	

		if(!isDryRun && kandyTestPlanAutmatedRunID!=null && !kandyTestPlanAutmatedRunID.isEmpty()) {
			try {
				KandyClientApiCaller.updateTestPlanAsCompleted(kandyTestPlanAutmatedRunID);
				ConfigLogger.logInfo("@TestSuite{ Completed .........}"); 
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			ConfigLogger.logInfo("@TestSuite{ DryRun=False}"); 
			suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, "DRY_RUN");  
		}
		
	}
    private long startTime;
}