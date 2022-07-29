package com.ktsapi.testng;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
//import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;

import com.ktsapi.dto.TestPlanRequest;
import com.ktsapi.dto.Testplan;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestNGMethod;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.exceptions.TestSuiteValidationException;
import com.ktsapi.kclient.KandyClientApiCaller;
import com.ktsapi.utils.AvtomatUtils;

public class AvtomatSuiteListner implements ISuiteListener  {

	//private static final Logger LOG = Logger.getLogger(AvtomatSuiteListner.class);
	private Integer testCount = 0 ; 
	private Testplan testPlan = null;
	private List<ITestNGMethod> testMethods = null;
	private String kandyTestPlanID;
	private String kandyTestPlanAutmatedRunID;
	boolean isDryRun = false; // TODO :: This need to fetch from config
	private String applicationId = "d5b0cef8-396d-11eb-adc1-0242ac120002"; // this need to get from Kandy client
	private Long workspaceId = 101L;

	 public void setTestCount(int testCount){
	      this.testCount = testCount;
	 }

	 public long getTestCount(){
	     return this.testCount;
	 }
	 
	@Override
	public void onStart(ISuite suite) {

		//startTime = System.currentTimeMillis();
		 
		 
		 testMethods  = suite.getAllMethods();
	     this.testCount = testMethods.size();
	     
	     // init TestPlan object
	     String testPlanUUID = AvtomatUtils.getUUID();
	     
	    // TODO : we may set these things to initial starter
		System.setProperty("test.suite.uuid", testPlanUUID);
		//PropertyConfigurator.configure(AvtomatSuiteListner.class.getResource("/log4j.properties"));
			
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
	     
	     //testPlan.setParm_baseUrl(suite.getXmlSuite().getParameter(TestSuiteParameters.BASE_URL));
	     
	     testPlan.setParm_sutVersionNumber(validateNumericParameterValueOf(TestSuiteParameters.SUT_VERSION_NUMBER,suite));
	     testPlan.setParm_implicitlyWaitTime(validateNumericParameterValueOf(TestSuiteParameters.IMPLICITLY_WAIT_TIME,suite));
	     testPlan.setParm_scriptTimeout(validateNumericParameterValueOf(TestSuiteParameters.SCRIPT_TIMEOUT,suite));
	     testPlan.setParm_pageLoadTimeout(validateNumericParameterValueOf(TestSuiteParameters.PAGE_LOAD_TIMEOUT,suite));	     
	     
	     testPlan.setBrowser(validateBrowserParameterValue(suite));
	     testPlan.setParm_baseUrl(validateBaseUrlParameterValue(suite));
	     testPlan.setTestDriver(validateTestDriverParameterValue(suite));
	     testPlan.setExecutionMode(validateExecutionModeParameterValue(suite));
	     
	     testPlan.setTestPlanRunId(validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_RUN_ID,suite));
	     testPlan.setTestPlanTemplateId(validateNumericParameterValueOf(TestSuiteParameters.TEST_PLAN_TEMPLATE_ID,suite));

//	     
	     //Long.parseLong(suite.getXmlSuite().getParameter(TestSuiteParameters.SCRIPT_TIMEOUT));
//	     validateNumericParameterValueOf(TestSuiteParameters.SCRIPT_TIMEOUT,suite);
	     

	     
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
	     
	     //suite.;
	     //System.out.println(">>>>>>>>>>>>>>>>>>>...  = "+ suite.getXmlSuite().getParameter("test-parm"));
	    
	     ConfigLogger.logInfo("@TestSuite{"+suite.getXmlSuite().getFileName()+"} is going to start...."); 
	     
//	     System.out.println("###baseurl = "+ suite.getXmlSuite().getParameter("baseUrl"));
	     //@TestSuite{com.ktsapi.element.test.SampleTest_1} is completed.
//	     System.out.println("### Test count = "+this.testCount);
//	     System.out.println("### Executed By = "+testPlan.getExecutedBy());
//	     System.out.println("### TEP Name = "+testPlan.getTestPlanName());
//	     System.out.println("### TEP ID = "+testPlan.getTestPlanid());
//	     System.out.println("### Executed On = "+ testPlan.getExecutionStartTimestamp()); 
	        
	     suite.setAttribute(TestInitializr.TEST_EXECUTED_BY, AvtomatUtils.getWindowsLoggedInUser());	    
	     suite.setAttribute(TestInitializr.TEST_PLAN_UUID, AvtomatUtils.getUUID());
	     suite.setAttribute(TestInitializr.TOTOAL_TESTS_IN_TEST_PLAN_XML, this.testCount);
	     suite.setAttribute(TestInitializr.TEST_PLAN_OBJ, testPlan);     
	     
	     suite.setAttribute(TestInitializr.IS_DRY_RUN, isDryRun);     
	     
	    String tepStartTimestamp = AvtomatUtils.localDateTimeStringFormat(testPlan.getExecutionStartTimestamp());
		TestPlanRequest testPlanRequest = new TestPlanRequest();
		testPlanRequest.setTestPlanName(testPlan.getTestPlanName());
		testPlanRequest.setExecutionStartTimestamp(tepStartTimestamp);
		testPlanRequest.setTotalTestCount(testPlan.getTotalTestsInTEPXml());
		testPlanRequest.setBrowser(testPlan.getBrowser());
		testPlanRequest.setBaseURL(testPlan.getParm_baseUrl());
		testPlanRequest.setTestDriver(testPlan.getTestDriver());
		testPlanRequest.setExecutionMode(testPlan.getExecutionMode());
		//testPlanRequest.setExecutionCompletedTimestamp("2020-12-31 23:59:59");
		testPlanRequest.setExecutedBy(testPlan.getExecutedBy());
		testPlanRequest.setTestSuiteFileName(testPlan.getTestSuiteFileName());
		testPlanRequest.setApplicationId(applicationId);
		testPlanRequest.setWorkspaceId(workspaceId);
		testPlanRequest.setModifiedTimestamp(tepStartTimestamp);
		
		testPlanRequest.setTestPlanTemplateId(testPlan.getTestPlanTemplateId());
		testPlanRequest.setTestPlanRunId(testPlan.getTestPlanRunId());
		
		
		if(!isDryRun) {
			
			try {
				TestPlanRequest response=null;
				
				// TODO : handle use case when runid is not provided , to initiate new Run
				// 	TODO : when run id is provided , no use of template id,
				// Logic : if both the ids are provided we can give priority to the the run id
				if(isTestPlanTemplateIdProvided(testPlan.getTestPlanTemplateId())) {
					if(isTestPlanRunIdIsProvided(testPlan.getTestPlanRunId())) { // there can be another use case that to run without TEPRunID and create a new run from script execution
						response = KandyClientApiCaller.postTestPlan(testPlanRequest);
					}else {
						ConfigLogger.logInfo("@TestSuite{ TestPlanRunId parameter is not provided}");
					}
				}else {
					ConfigLogger.logInfo("@TestSuite{ TestPlanTemplateId parameter is not provided }");
				}
				
				
				if(response!=null ) {
//					if(response.getTestPlanRunId()!=null && response.getTestPlanRunId()!="") {
//						ConfigLogger.logInfo("@TestSuite{"+ response.getTestPlanRunId() +" Initiated .........}");
//						kandyTestPlanID = response.getTestPlanRunId();
//						suite.setAttribute(TestCache.KANDY_CLIENT_TEST_PLAN_ID, kandyTestPlanID); 
//					}else {
//						suite.setAttribute(TestCache.KANDY_CLIENT_TEST_PLAN_ID, "UNDEFINED");
//					}
					if(response.getTestPlanRunId()!=null && response.getTestPlanRunId()!="") {						
						kandyTestPlanID = response.getTestPlanRunId();						
						kandyTestPlanAutmatedRunID = response.getTestPlanAutomatedRunId();
						ConfigLogger.logInfo("@TestSuite{ TestAutomatedRunId-"+ kandyTestPlanAutmatedRunID +" initiated under Test Plan "+ kandyTestPlanID + "..........}");
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
			ConfigLogger.logInfo("@TestSuite{ DryRun=False}"); 
			suite.setAttribute(TestInitializr.KANDY_CLIENT_TEST_PLAN_ID, "DRY_RUN");  
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
	
	private String validateBrowserParameterValue(ISuite suite) {
		String browserParmValue = suite.getXmlSuite().getParameter(TestSuiteParameters.BROWSER);
		// TODO : Write generic validation, to validate all values of Browser enum instead one by one
		if(browserParmValue!=null) {
			
			try {
				Browsers.valueOf(browserParmValue.toUpperCase());
			}catch(IllegalArgumentException e) {
				String errMsg = "Invalid value for \"browser\" parameter. Value Should be one of " + Arrays.asList(Browsers.values()) ; // get list form Browser enum
				ConfigLogger.logError(errMsg);
				throw new TestSuiteValidationException(errMsg);
			}

		}else {
			return Browsers.UNDEFINED.name();
		}
		
		return browserParmValue.toUpperCase();
	}

	@Override
	public void onFinish(ISuite suite) {
		//System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
		long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);		
		
		ConfigLogger.logInfo("@TestSuite{"+suite.getXmlSuite().getFileName()+"} execution has completed, took around " + seconds + " seconds"); 	
		
		if(!isDryRun && kandyTestPlanID!=null && !kandyTestPlanID.isEmpty()) {
			try {
				KandyClientApiCaller.updateTestPlanAsCompleted(kandyTestPlanID);
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
	
	//implements IExecutionListener
    private long startTime;
// 
//    @Override
//    public void onExecutionStart() {
//        startTime = System.currentTimeMillis();
//        System.out.println("TestNG is going to start");     
//    }
// 
//    @Override
//    public void onExecutionFinish() {
//        System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
//    }
}