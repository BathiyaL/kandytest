package com.ktsapi.testng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.IClass;
import org.testng.IConfigurationListener2;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlSuite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.contexts.TestSuiteParameters;
import com.ktsapi.core.Runner;
import com.ktsapi.core.TestContext;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.core.TestRunner;
import com.ktsapi.dto.TestResultRequest;
import com.ktsapi.enums.TestResultStatus;
import com.ktsapi.kclient.KandyClientApiCaller;
import com.ktsapi.testng.reports.CustomReportGenerator;
import com.ktsapi.utils.AvtomatUtils;
import static com.ktsapi.CommonActions.saveScreenshot;

public class AvtomatTestListner implements ITestListener, IConfigurationListener2, IReporter {

	private Runner runner = null;
	LocalDateTime testExecutionStartTime;

	boolean postTestResult(TestResultStatus testResultStatus,ITestResult result) {
		
		String ationLog = printAndGetActionLogger();
		
		if (TestInitializr.getKandyClientTestPlanId() == null
				|| TestInitializr.getKandyClientTestPlanId().equals("UNDEFINED")) {
			return false;
		}

		TestResultRequest testResultRequest = new TestResultRequest();

		testResultRequest.setBaseURL(TestInitializr.getTestConfiguration().getBaseUrl());
		testResultRequest.setBrowser(TestInitializr.getTestConfiguration().getBrowser().getBrowserName().toUpperCase());// TODO: this uppercase should handle when saving to testcache
		testResultRequest.setExecutionCompletedTimestamp(
				AvtomatUtils.localDateTimeStringFormat(AvtomatUtils.getCurretnTimeStamp()));
		testResultRequest.setExecutionMode(TestInitializr.getTestConfiguration().getExecutionMode().toString());
		testResultRequest.setExecutionNode("bladduwahetty");// TODO: for now hardcoded need to handle this
		testResultRequest.setExecutionStartTimestamp(AvtomatUtils.localDateTimeStringFormat(testExecutionStartTime)); // Need to pass start time

		testResultRequest.setTestName(TestInitializr.getTestName());
		testResultRequest.setTestDriver(TestInitializr.getTestConfiguration().getTestDriver().toString());
		testResultRequest.setTestExecutionLog(ationLog);
		testResultRequest.setTestClass(TestInitializr.getTestClassName());
		testResultRequest.setTestResultStatus(testResultStatus.name());

		testResultRequest.setTestPlanId(TestInitializr.getKandyClientTestPlanId());
		testResultRequest.setTestPlanAutomatedRunId(TestInitializr.getKandyClientTestPlanAutomatedRunId());
		// getting testid from TEP is there better way to handle this
		String[] nampeSplit = result.getTestContext().getName().split("\\."); //TestInitializr.getTestClassName().split("\\.");
		String testClassName = nampeSplit[nampeSplit.length - 1];
		String testID = null;
		if (testClassName.contains("_") && testClassName.contains("TC")) {
			testID = "TC-" + (testClassName.split("\\_")[0].split("TC"))[1];
		}
		testResultRequest.setTestCaseId(testID);
		// String testID = testClassName

		// TODO : if we are execute form selenium grid we may need to pass relevant node
		// ip
		String executionNode = AvtomatUtils.getWindowsLoggedInUser();
		testResultRequest.setExecutionNode(executionNode);

			try {
				KandyClientApiCaller.postTestResult(testResultRequest);
				return true;
			} catch (Exception e) {
				e.printStackTrace(); // TODO : Add custom exception
			}		
		return false;
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ConfigLogger.logInfo(getTestMethodFromITResult(result) + " has failed");
		saveFailureScreenshot();
		tearDownContext(result, TestResultStatus.Failed);
	}
	
	private void saveFailureScreenshot() {
		saveScreenshot("FailPoint");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ConfigLogger.logInfo(getTestMethodFromITResult(result) + " has skipped");
		// TODO: if there is a validation ERROR in test tag in suite (means before test start) no need to tearDown or do we need to log 
		// result with validation ERROR
		tearDownContext(result, TestResultStatus.Skipped);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ConfigLogger.logInfo(getTestMethodFromITResult(result) + " is success");
		tearDownContext(result, TestResultStatus.Passed);
	}

	private String getTestMethodFromITResult(ITestResult result) {
		String testClassName = result.getTestClass().getName();
		String testMethodLogStr = "@Test{" + testClassName + "|" + result.getMethod().getMethodName() + "()}";
		return testMethodLogStr;
	}

	// @Override
	public void onTestStart(ITestResult result) {
		/*
		 * Not @beforeTest in the script hence @Test has to init the TestCache
		 */
		String testClassName = result.getMethod().getTestClass().getName();
		logMethodStart("@Test", result.getMethod().getMethodName() + "()", testClassName);
		if (!isTestClassContaisAnnotation(result.getInstance().getClass(), BeforeTest.class)) {
			setupTestContext(result);
		}
	}

	boolean isTestClassContaisAnnotation(Class<? extends Object> clazz, final Class<? extends Annotation> annotation) {
		for (final Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * This will execute before any configuration to init test cache
	 * before @beoforetest we have to init before onTestStart onStart : not idle
	 * when we have multiple classes in a test tag in xml hence try beforeInvocation
	 * or beforeConfiguration -
	 */

	Map<String, String> xmlTestLevelParameterMap;

	@Override
	public void onStart(ITestContext testContext) {

		setXmlTestLevelParametersToMap(testContext);
		testExecutionStartTime = AvtomatUtils.getCurretnTimeStamp();
		logMethodStart("@TestClass", "", testContext.getCurrentXmlTest().getName());
	}

	private void setXmlTestLevelParametersToMap(ITestContext testContext) {
		TestSuiteValidator testSuiteValidator = new TestSuiteValidator(testContext);
		
		// TODO : Need to validate other parm like browser
		xmlTestLevelParameterMap = new HashMap<String, String>();
		xmlTestLevelParameterMap.put(TestSuiteParameters.BASE_URL,
				testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.BASE_URL));
		xmlTestLevelParameterMap.put(TestSuiteParameters.IMPLICITLY_WAIT_TIME,
				testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.IMPLICITLY_WAIT_TIME));
		xmlTestLevelParameterMap.put(TestSuiteParameters.SCRIPT_TIMEOUT,
				testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.SCRIPT_TIMEOUT));
		xmlTestLevelParameterMap.put(TestSuiteParameters.PAGE_LOAD_TIMEOUT,
				testContext.getCurrentXmlTest().getParameter(TestSuiteParameters.PAGE_LOAD_TIMEOUT));
		xmlTestLevelParameterMap.put(TestSuiteParameters.BROWSER, testSuiteValidator.validateAndGetBrowserParameterValue());

	}

	private void logMethodStart(String prefix, String methodName, String testClassName) {
		if (!methodName.isEmpty()) {
			ConfigLogger.logInfo(prefix + "{" + testClassName + "|" + methodName + "} is going to start ...");
		} else {
			ConfigLogger.logInfo(prefix + "{" + testClassName + "} is going to start ...");
		}
	}

	private void tearDownContext(ITestResult result, TestResultStatus testResultStatus) {
		if (runner != null) {
			if(!TestInitializr.getDryRunStatus()) {
				postTestResult(testResultStatus,result);
			}else {
				printAndGetActionLogger();
			}
			runner.end();
		}
	}

	private void setupTestContext(ITestResult result) {

		if (TestInitializr.isTestCacheInUse()) {
			return;
		}
		result.setAttribute(TestSuiteParameters.XML_TEST_LEVEL_MAP, xmlTestLevelParameterMap);
		TestContext testContext = new TestngTestContext(result);
		if (!testContext.hasTestConfigurationAnnotation()) {
			throw new SkipException("@TestConfiguration not defined in test");
		}

		// Setting data to TestCache
		runner = new TestRunner(testContext);
		runner.start(); // Open/Init TestCache map in TestInitializr

		String log = "\n#################### Test Startup Configuration #####################\n" + "Test Suite : "
				+ TestInitializr.getTestPlanName() + "["
				+ TestInitializr.getTestSuiteFilePath().getFileName().toString() + "]" + "\n" + "Test Name : "
				+ TestInitializr.getTestName() + "\n" + "Test Class : " + TestInitializr.getTestClassName() + "\n"
				+ "baseUrl : " + TestInitializr.getTestConfiguration().getBaseUrl() + "\n" + "testDriver : "
				+ TestInitializr.getTestConfiguration().getTestDriver() + "\n" + "browser : "
				+ TestInitializr.getTestConfiguration().getBrowser() + "\n" + "Running OS : "
				+ AvtomatUtils.getOS() + "\n"
				+ "implicitlyWaitTime : " + TestInitializr.getImplicitlyWaitTime() + "\n" + "pageLoadTimeout : "
				+ TestInitializr.getPageLoadTimeout() + "\n" + "scriptTimeout : " + TestInitializr.getScriptTimeout()
				+ "\n" + "######################################################################";
		ConfigLogger.logInfo(log);
	}

	@Override
	public void onFinish(ITestContext context) {
		ConfigLogger.logInfo("@TestClass{" + context.getCurrentXmlTest().getName() + "} execution has finished. \n");
	}

	/*
	 * TODO : this may no need to print on the console
	 */
	private String printAndGetActionLogger() {
		String json = "{}";
		try {
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			json = gson.toJson(TestInitializr.getTestActionsList()); 
			ConfigLogger.logInfo(json);
		} catch (Exception e) {
			String errorMessage = "{\"ERROR\":\"Error occurred while extracting the action logger\",\"ERROR MESSAGE\":\""+e.getMessage()+"\"}";
			ConfigLogger.logInfo(errorMessage);
			return errorMessage;
		}
		return json;
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		tearDownContext(result, TestResultStatus.Failed); // TODO : This test status need to check

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
		isConfigurationSetupInScript = true;

		logMethodStart("@BeforeTests", tr.getMethod().getMethodName() + "()", tr.getTestClass().getName());
		/*
		 * logic is to make sure setupTestContext() is triggered only
		 * for @BeforeTest(Configuration) methods
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
		String testMethodLogStr = "@BeforeTest{" + result.getTestClass().getName() + "|"
				+ result.getMethod().getMethodName() + "()}";
		return testMethodLogStr;
	}
	private long testCount = 0;

	public void setTestCount(long testCount) {
		this.testCount = testCount;
	}

	public long getTestCount() {
		return this.testCount;
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		CustomReportGenerator customReport =  new CustomReportGenerator();
		customReport.generateReport(xmlSuites, suites, outputDirectory);
	}
	
}
