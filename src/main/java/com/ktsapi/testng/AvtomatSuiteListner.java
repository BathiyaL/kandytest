package com.ktsapi.testng;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
    private long startTime;

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
	     
	     testPlan.setParm_sutVersionNumber(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.SUT_VERSION_NUMBER));
	     testPlan.setParm_implicitlyWaitTime(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.IMPLICITLY_WAIT_TIME));
	     testPlan.setParm_scriptTimeout(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.SCRIPT_TIMEOUT));
	     testPlan.setParm_pageLoadTimeout(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.PAGE_LOAD_TIMEOUT));	     

	     testPlan.setBrowser(testSuiteValidator.validateAndGetBrowserParameterValue());
	     testPlan.setParm_baseUrl(testSuiteValidator.validateBaseUrlParameterValue());
	     testPlan.setTestDriver(testSuiteValidator.validateTestDriverParameterValue());
	     testPlan.setExecutionMode(testSuiteValidator.validateExecutionModeParameterValue());
	     
	     testPlan.setTestPlanRunId(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_RUN_ID));
	     testPlan.setTestPlanTemplateId(testSuiteValidator.validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_TEMPLATE_ID));	     
	     testPlan.setParm_overrideTestParameters(testSuiteValidator.validateOverrideTestParameterValue());

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
}