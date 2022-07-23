package com.ktsapi.testng;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
//import org.dizitart.no2.objects.ObjectRepository;
//import org.hibernate.Session;

import org.testng.IClass;
import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktsapi.actions.core.ActionsLogger;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.Runner;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.core.TestContext;
import com.ktsapi.core.TestRunner;
import com.ktsapi.core.TestSuiteContext;
import com.ktsapi.dto.TestPlanRequest;
import com.ktsapi.dto.TestResultRequest;
import com.ktsapi.dto.Testplan;
import com.ktsapi.enums.TestResultStatus;
import com.ktsapi.kclient.KandyClientApiCaller;
import com.ktsapi.utils.AvtomatUtils;

public class AvtomatTestListner implements ITestListener, IConfigurationListener2 {
//public class AvtomatTestNGListner extends TestListenerAdapter {	

//    private static final Logger LOG = Logger.getLogger(AvtomatTestListner.class);
    //private static final AvtomatBotWebDriverActionsImpl actions = new AvtomatBotWebDriverActionsImpl();
    private Runner runner = null;
    LocalDateTime testExecutionStartTime;
    boolean isDryRun = true; // this will override from TEP configurations
    //String testClassName;
    
    boolean postTestResult(TestResultStatus testResultStatus) {
    	
    	if(TestInitializr.getKandyClientTestPlanId()==null || TestInitializr.getKandyClientTestPlanId()=="UNDEFINED") {
    		return false;
    	}
    	
    	String ationLog = printActionLogger();
    	
    	TestResultRequest testResultRequest = new TestResultRequest();
    	
    	testResultRequest.setBaseURL(TestInitializr.getTestConfiguration().getBaseUrl());
    	testResultRequest.setBrowser(TestInitializr.getTestConfiguration().getBrowser().getBrowserName().toUpperCase());// TODO: this upper case should handle when saving to testcache
    	testResultRequest.setExecutionCompletedTimestamp(AvtomatUtils.localDateTimeStringFormat(AvtomatUtils.getCurretnTimeStamp()));
    	testResultRequest.setExecutionMode(TestInitializr.getTestConfiguration().getExecutionMode().toString());
    	testResultRequest.setExecutionNode("bladduwahetty");// TODO: for now hardcoded need to handle this
    	testResultRequest.setExecutionStartTimestamp(AvtomatUtils.localDateTimeStringFormat(testExecutionStartTime)); // Need to pass start time
    	//"testCaseId": "TC-5",
//    	testResultRequest.setTestCaseId("TC-11");// TODO: for now hardcoded need to handle this
    	testResultRequest.setTestName(TestInitializr.getTestName());
    	testResultRequest.setTestDriver(TestInitializr.getTestConfiguration().getTestDriver().toString());
    	testResultRequest.setTestExecutionLog(ationLog);
    	//"testExecutionType": "AUTOMATED",
    	testResultRequest.setTestClass(TestInitializr.getTestClassName());
    	testResultRequest.setTestResultStatus(testResultStatus.name());
    	
    	testResultRequest.setTestPlanId(TestInitializr.getKandyClientTestPlanId());
    	testResultRequest.setTestPlanAutomatedRunId(TestInitializr.getKandyClientTestPlanAutomatedRunId());
    	// "testPlanAutomatedRunId": 6
    	String[] nampeSplit = TestInitializr.getTestClassName().split("\\.");
    	String testClassName = nampeSplit[nampeSplit.length-1];
    	String testID=null;
    	if(testClassName.contains("_") && testClassName.contains("TC")) {
    		testID = "TC-" + (testClassName.split("\\_")[0].split("TC"))[1];
    	}
    	testResultRequest.setTestCaseId(testID);
    	//String testID = testClassName
   	
    	 
    	// TODO : if we are execute form selenium grid we may need to pass relevant node ip
    	String executionNode = AvtomatUtils.getWindowsLoggedInUser();
    	testResultRequest.setExecutionNode(executionNode);
		
		if(!isDryRun && testID!=null && testID.contains("TC-")) {
			try {
				KandyClientApiCaller.postTestResult(testResultRequest);
				//ConfigLogger.logInfo("@TestSuite{"+ response.getTestPlanId() +" Initiated .........}"); 	
				return true;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//ConfigLogger.logInfo("@TestSuite{ DryRun=False}"); 
			// TODO : add some logs
		}    
		
		return false;
    }

    @Override
    public void onTestFailure(ITestResult result) {

    	ConfigLogger.logInfo(getTestMethodFromITResult(result) + " has failed");
        tearDownContext(result,TestResultStatus.Failed);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //String testMethod = result.getMethod().toString();
        //LOG.info("@Test"+testMethod.substring(0, testMethod.indexOf("[")) + " is skipped");
    	ConfigLogger.logInfo(getTestMethodFromITResult(result) + " has skipped");
        tearDownContext(result,TestResultStatus.Skipped);
    }

    @Override
    public void onTestSuccess(ITestResult result) {        
    	ConfigLogger.logInfo(getTestMethodFromITResult(result) + " is success");    	
        tearDownContext(result,TestResultStatus.Passed);
    }

    private String getTestMethodFromITResult(ITestResult result) {
    	 //String testMethod = result.getMethod().toString();
    	 //String testMethodOnly = testMethod.substring(0, testMethod.indexOf("[")).split("\\.")[1];
    	//String testClassName = result.getCurrentXmlTest().getName();
    	 String testClassName = result.getTestClass().getName();
    	 String testMethodLogStr = "@Test{"+testClassName+"|"+result.getMethod().getMethodName() + "()}";
         return testMethodLogStr;
    }
    //@Override
    public void onTestStart(ITestResult result) {    	
    	/*
    	 * Not @beforeTest in the script hence @Test has to init the TestCache
    	 */
    	String testClassName = result.getMethod().getTestClass().getName();
    	logMethodStart("@Test",result.getMethod().getMethodName() + "()",testClassName);
    	if(!isTestClassContaisAnnotation(result.getInstance().getClass(),BeforeTest.class)) {
    		setupTestContext(result);    		
    	}

    	//setupTestContext(result);  
    	
    	
        //System.out.println("//////////////////////////////////////////////////<Test START>////////////////////////////////////////////////////// ");
        
        //System.out.println(result.getTestContext().getSuite().getName());
        //System.out.println(result.getTestContext().getSuite().getAllMethods().size());
        //System.out.println(result.getTestContext().getSuite().getSuiteState().toString());
    }
    
    boolean isTestClassContaisAnnotation(Class<? extends Object> clazz,final Class<? extends Annotation> annotation) {
        for (final Method method : clazz.getDeclaredMethods()) {
        	if (method.isAnnotationPresent(annotation)) {
        		return true;
        	}    		 
    	 }
        return false;
    }
    
    /*
     * This will execute before any configuration
     * to init test cache before @beoforetest we have to init before onTestStart
     * onStart : not idle when we have multiple classes in a test tag in xml 
     * hence try beforeInvocation or beforeConfiguration
     * - 
     */
    
    Map<String, String> xmlTestLevelParameterMap ;
    @Override
    public void onStart(ITestContext testContext) {
    	//System.out.println("//////////////////////////////////////////////////<Test START>////////////////////////////////////////////////////// ");

    	setXmlTestLevelParametersToMap(testContext);
    	isDryRun = (boolean) testContext.getSuite().getAttribute(TestInitializr.IS_DRY_RUN);
    
    	//String testClassName = testContext.getCurrentXmlTest().getXmlClasses().get(0).getName();
    	String testClassName = testContext.getCurrentXmlTest().getName();
//    	System.out.println("############################################################  testClassName :: " + testClassName);
    	testExecutionStartTime = AvtomatUtils.getCurretnTimeStamp();
    	
    	//LOG.info("{"+testClassName + "} is going to start ...");
    	logMethodStart("@TestClass","",testClassName);
    }

    private void setXmlTestLevelParametersToMap(ITestContext testContext) {
    	xmlTestLevelParameterMap = new HashMap<String, String>();
    	xmlTestLevelParameterMap.put(TestSuiteParameters.BASE_URL, testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.BASE_URL));    	
    	xmlTestLevelParameterMap.put(TestSuiteParameters.IMPLICITLY_WAIT_TIME, testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.IMPLICITLY_WAIT_TIME));
    	xmlTestLevelParameterMap.put(TestSuiteParameters.SCRIPT_TIMEOUT, testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.SCRIPT_TIMEOUT));
    	xmlTestLevelParameterMap.put(TestSuiteParameters.PAGE_LOAD_TIMEOUT, testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.PAGE_LOAD_TIMEOUT));
    	xmlTestLevelParameterMap.put(TestSuiteParameters.BROWSER, testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.BROWSER));

    }
    private void logMethodStart(String prefix, String methodName, String testClassName) {
    	if(!methodName.isEmpty()) {
    		ConfigLogger.logInfo(prefix+"{"+testClassName +"|"+methodName+ "} is going to start ...");
    	}else {
    		ConfigLogger.logInfo(prefix+"{"+testClassName + "} is going to start ...");
    	}    	
    }
    private void tearDownContext(ITestResult result, TestResultStatus testResultStatus) {
    	if (runner != null) {
    		//printActionLogger();
    		postTestResult(testResultStatus);
    	    runner.end();    	    
    	}
    }
    
    private void setupTestContext(ITestResult result) {
	    
    	if (TestInitializr.isTestCacheInUse()) {
    	    return;
    	}
    	result.setAttribute(TestSuiteParameters.XML_TEST_LEVEL_MAP, xmlTestLevelParameterMap);
        //System.out.println("++++++++++ :: " + Thread.currentThread().getId());
    	TestContext testContext = new TestngTestContext(result);     
    	if (!testContext.hasTestConfigurationAnnotation()) {
            throw new SkipException("@TestConfiguration not defined in test");
        }
    	
    	// Setting data to TestCache
        runner = new TestRunner(testContext);
        
        runner.start(); // set to the the TestCache
        
		String log = "\n#################### Test Startup Configuration #####################\n" + 
				"Test Suite : " + TestInitializr.getTestPlanName() + "[" + TestInitializr.getTestSuiteFilePath().getFileName().toString() + "]" + "\n" + 
				"Test Name : " + TestInitializr.getTestName() + "\n" + 
				"Test Class : " + TestInitializr.getTestClassName() + "\n" + 
				"baseUrl : " + TestInitializr.getTestConfiguration().getBaseUrl() + "\n" + 
				"testDriver : " + TestInitializr.getTestConfiguration().getTestDriver() + "\n" +  
				"browser : " + TestInitializr.getTestConfiguration().getBrowser() + "\n" +  
				"browserVersion : " + TestInitializr.getTestConfiguration().getBrowserVersion() + "\n" +  
				"implicitlyWaitTime : " + TestInitializr.getImplicitlyWaitTime() + "\n" +  
				"pageLoadTimeout : " + TestInitializr.getPageLoadTimeout() + "\n" +  
				"scriptTimeout : " + TestInitializr.getScriptTimeout() + "\n" +  
				"######################################################################";		
		ConfigLogger.logInfo(log);

   }

    @Override
    public void onFinish(ITestContext context) {
    	
    	ConfigLogger.logInfo("@TestClass{"+context.getCurrentXmlTest().getName() + "} execution has finished. \n");
    	//ConfigLogger.logInfo("@TestClass{"+testClassName + "} execution has finished. \n");
    }

//    void setWebDriverNullSkippedStatus(ITestResult result) {
//        String testMethod = result.getMethod().toString();
//        LOG.info("[Script] @Test[" + testMethod.substring(0, testMethod.indexOf("[")) + "] -> Skipped");
//        result.setStatus(ITestResult.SKIP);
//        Throwable thr = new SkipException("Browser not initalized (Call OpenBrowser() action)");
//        result.setThrowable(thr);
//    }

//    void onTestFailureLogger(ITestResult result) {
//        String testMethod = result.getMethod().toString();
//        LOG.info("[Script] @Test[" + testMethod.substring(0, testMethod.indexOf("[")) + "] -> Failure");
//    }

    /*
     * TODO : this may no need to print on the console
     */
    private String printActionLogger() {    	
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(TestInitializr.getTestActionsList());
        System.out.println(json);
        return json;
    }


	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		tearDownContext(result,TestResultStatus.Failed); // TODO : This test status need to check
		
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {
		ConfigLogger.logInfo(getBeforeTestMethodFromITResult(itr) + " is success");		
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		ConfigLogger.logInfo(getBeforeTestMethodFromITResult(itr) + " is failed");		
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
		ConfigLogger.logInfo(getBeforeTestMethodFromITResult(itr) + " is skipped");		
	}

	IClass testInstanceClass = null;
	boolean isConfigurationSetupInScript = false;
	@Override
	public void beforeConfiguration(ITestResult tr) {
		isConfigurationSetupInScript=true;
		
		//System.out.println("#####beforeConfiguration :: " + tr.getTestClass());
		//System.out.println("#####beforeConfiguration :: " + tr.getMethod().getMethodName());
		//System.out.println("##### " + tr.getInstance());
		//System.out.println("##### " + tr.getMethod().isBeforeTestConfiguration());
	
		logMethodStart("@BeforeTests",tr.getMethod().getMethodName() + "()", tr.getTestClass().getName());
		/*
		 * logic is to make sure setupTestContext() is triggered only for @BeforeTest(Configuration) methods
		 */
		if (tr.getMethod().isBeforeTestConfiguration()) {
			if (testInstanceClass == null) { // this is to verify first beforeTest initiation when there is multiple
				setupTestContext(tr);
				testInstanceClass = tr.getTestClass();
			} else {
				if (!testInstanceClass.equals(tr.getTestClass())) { // go to different test class instance in the same plan
					testInstanceClass = tr.getTestClass();
					setupTestContext(tr);
				}
			}
		}		
	}

   private String getBeforeTestMethodFromITResult(ITestResult result) {
   	 String testMethodLogStr = "@BeforeTest{"+result.getTestClass().getName()+"|"+result.getMethod().getMethodName() + "()}";
        return testMethodLogStr;
   }
	
	private long testCount = 0 ; 
	private long startTime;
	 private List<ITestNGMethod> testMethods = null;

	 public void setTestCount(long testCount){
	      this.testCount = testCount;
	 }

	 public long getTestCount(){
	     return this.testCount;
	 }
	 
//	@Override
//	public void onStart(ISuite suite) {
//		startTime = System.currentTimeMillis();
//		 System.out.println("TestNG is going to start....");    
//		 
//		 testMethods  = suite.getAllMethods();
//	     this.testCount = testMethods.size();
//	     
//	     System.out.println("### Test count = "+this.testCount); 
//	     
////	     TestSuiteContext testSuiteContext = new TestngTestSuiteContext(suite);        
////	     runner = new TestRunner(testSuiteContext);
//		
//	}
//
//	@Override
//	public void onFinish(ISuite suite) {
//		System.out.println("TestNG has finished, took around " + (System.currentTimeMillis() - startTime) + "ms");
//		
//	}
//    

    

}
