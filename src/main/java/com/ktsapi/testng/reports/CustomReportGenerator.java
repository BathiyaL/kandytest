package com.ktsapi.testng.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.core.Const;
import com.ktsapi.testng.AvtomatTestListner;
import com.ktsapi.testng.TestNgUtil;
import com.ktsapi.utils.AvtomatUtils;

public class CustomReportGenerator {

    String REPORT_NAME = "testreport.html";
	private String reportFolder= "kandyreports";
	private String suiteFolder= "TestSuite";

	private static final String ROW_TEMPLATE = "<tr><td>%s</td><td>%s</td><td class=\"%s\">%s</td><td style=\"text-align: center; vertical-align: middle;\">%s</td></tr>";

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

		String suiteName = suites.get(0).getName();	
		if(suiteName != null && !suiteName.trim().isEmpty()) {
			suiteFolder = suiteName;
			REPORT_NAME = "KandyTestReport_"+suiteName.concat(".html");
		}
		
    	int testsCount = 0;
        int passedTests = 0;
        int skippedTests = 0;
        int failedTests = 0;
        String reportTemplate = initReportTemplate();
        
        if(reportTemplate!=null) {
    		for(String key :  suites.get(0).getResults().keySet()) {
    	    	testsCount += suites.get(0).getResults().get(key).getTestContext().getPassedTests().size();
    	        passedTests += suites.get(0).getResults().get(key).getTestContext().getPassedTests().size();
    	        skippedTests += suites.get(0).getResults().get(key).getTestContext().getSkippedTests().size();
    	        failedTests += suites.get(0).getResults().get(key).getTestContext().getFailedTests().size();			
    		}
    		testsCount = passedTests + skippedTests + failedTests;
    		reportTemplate = reportTemplate.replace("$reportTitle", getReportTitle(suiteName));
    		reportTemplate = reportTemplate.replace("$passedTestCount", Integer.toString(passedTests));
    		reportTemplate = reportTemplate.replace("$failedTestCount", Integer.toString(failedTests));
    		reportTemplate = reportTemplate.replace("$skippedTestCount", Integer.toString(skippedTests));
    		
    		
    		final String body = suites.stream().flatMap(suiteToResults()).collect(Collectors.joining());
    		saveReportTemplate(outputDirectory, reportTemplate.replace("$testResults",body));
        }else {
        	ConfigLogger.logInfo("Error occuer while initializing custom report template");
        }
	}
	
    protected String getReportTitle(String title) {
		return title + " [" + AvtomatUtils.getCurrentDateTimeInSimpleFormat() + "]" ;
	}

	private Function<ISuite, Stream<? extends String>> suiteToResults() {
		return suite -> suite.getResults().entrySet().stream().flatMap(resultsToRows());
	}

	private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows() {
		return e -> {
			ITestContext testContext = e.getValue().getTestContext();

			Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
			Set<ITestResult> passedTests = testContext.getPassedTests().getAllResults();
			Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();		

			return Stream.of(failedTests, passedTests, skippedTests)
					.flatMap(results -> generateReportRows(results).stream());
		};
	}

	private List<String> generateReportRows(Set<ITestResult> allTestResults) {
		return allTestResults.stream().map(testResultToResultRow()).toList();
	}

	private Function<ITestResult, String> testResultToResultRow() {
		return testResult -> {
			String fullyQualifiedName = testResult.getTestClass().getName();
			String testClassName = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".")+1);
			TestNgUtil testngUtil = new TestNgUtil(testResult);
			String testName = testngUtil.getTestID() + " : " + testngUtil.getTestName();

			switch (testResult.getStatus()) {
			case ITestResult.FAILURE:
				return String.format(ROW_TEMPLATE, testClassName, testName, "bg-danger", "FAILED", "NA");

			case ITestResult.SUCCESS:
				return String.format(ROW_TEMPLATE, testClassName, testName, "bg-success", "PASSED",
						String.valueOf(testResult.getEndMillis() - testResult.getStartMillis()));

			case ITestResult.SKIP:
				return String.format(ROW_TEMPLATE, testClassName, testName, "bg-warning", "SKIPPED","NA");

			default:
				return "";
			}
		};
	}

	private String initReportTemplate() {
		String template = null;
		byte[] reportTemplate = null;
			
		ClassLoader classLoader = AvtomatTestListner.class.getClassLoader();       
        InputStream inputStream = classLoader.getResourceAsStream(Const.CUSTOM_REPORT_TEMPLATE_PATH);

        if (inputStream != null) {
            try {
            	reportTemplate = new byte[inputStream.available()];
            	inputStream.read(reportTemplate);
				inputStream.close();
				template = new String(reportTemplate, "UTF-8");
			} catch (Exception e) {
				ConfigLogger.logError("[AvtomatTestListner] -> Error occuer while reading custom report template");
			}
        } else {
        	ConfigLogger.logError("[AvtomatTestListner] -> report template not exists");
        }

        return template;
	}

	private void saveReportTemplate(String outputDirectory, String reportTemplate) {
		
		// create custom report path to hold custom reports only
		File outputDirectoryFilePath = new File(outputDirectory,reportFolder);
		outputDirectoryFilePath.mkdirs();
		try {
			PrintWriter reportWriter = new PrintWriter(
					new BufferedWriter(new FileWriter(new File(outputDirectoryFilePath.getPath(), REPORT_NAME))));
			reportWriter.println(reportTemplate);
			reportWriter.flush();
			reportWriter.close();
		} catch (IOException e) {
			ConfigLogger.logInfo("Error occuer while saving custom report template");
		}
		saveReportInSuiteFolder(outputDirectory,reportTemplate);
	}
	
	private void saveReportInSuiteFolder(String outputDirectory, String reportTemplate) {
		File outputDirectoryFilePath = new File(outputDirectory,suiteFolder);
		outputDirectoryFilePath.mkdirs();
		try {
			PrintWriter reportWriter = new PrintWriter(
					new BufferedWriter(new FileWriter(new File(outputDirectoryFilePath.getPath(), REPORT_NAME))));
			reportWriter.println(reportTemplate);
			reportWriter.flush();
			reportWriter.close();
		} catch (IOException e) {
			ConfigLogger.logInfo("Error occuer while saving custom report template");
		}
	}

}
