package testapi;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ktsapi.dto.TestPlanRequest;
import com.ktsapi.dto.TestResultRequest;
import com.ktsapi.dto.Testplan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;

public class Test {
	public static void main(String[] args) throws URISyntaxException {
		//URL url = Test.class.getResource("chromedriver.exe");
		//System.out.println(url.toURI());
		
		//URL f = Test.class.getClassLoader().getResource("resources/drivers/chromedriver.exe");
		//System.out.println(f.toURI());
		
		//System.out.println(System.getProperty("user.dir"));
		
		//ClassLoader classLoader = Test.class.getClassLoader();
		//File file = new File(classLoader.getResource("").getFile());
		
//		URL url = Test.class.getClassLoader().getResource("drivers/chromedriver.exe");
//		System.out.println(url.toURI());
		
		try {
			//postTestPlan();
			postTestResult();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("######################## COMPLETED #######################");
		//getTestPlan();
		
		
		

	}
	
	public static void postTestPlan() throws ClientProtocolException, IOException {
		
//		Testplan testPlan2 = new Testplan("tep3");
//		testPlan2.setTestPlanName("AQR");
//		
//		GsonBuilder gsonBuilder = new GsonBuilder();  
//		gsonBuilder.serializeNulls();  
//		Gson gson = gsonBuilder.create();		
//		
//		System.out.println("--------- " + gson.toJson(testPlan2));
//		
//		/*
//		 * when we create json from this it take as testPlanID, but in db it has created as testPlanId
//		 * seems method is in simple case, drop the table and try
//		 */
//		
//		String       postUrl       = "http://localhost:8080/testPlan";// put in your url
//	
//		HttpClient   httpClient    = HttpClientBuilder.create().build();
//		HttpPost     post          = new HttpPost(postUrl);
//		StringEntity postingString = new StringEntity(gson.toJson(testPlan2));//gson.tojson() converts your pojo to json
//		post.setEntity(postingString);
//		post.setHeader("Content-type", "application/json");
//		HttpResponse  response = httpClient.execute(post);
		
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();  
		Gson gson = gsonBuilder.create();
		
		TestPlanRequest testPlanRequest = new TestPlanRequest();
		testPlanRequest.setTestPlanName("REGRESSION_3");
		testPlanRequest.setExecutionStartTimestamp("2020-12-31 22:59:59");
		testPlanRequest.setTotalTestCount(100);
		testPlanRequest.setBrowser("CHROME");
		testPlanRequest.setBaseURL("http://example.com/");
		testPlanRequest.setTestDriver("WEB");
		testPlanRequest.setExecutionMode("DEFAULT");
		testPlanRequest.setModifiedTimestamp("2020-12-31 23:59:59");
		testPlanRequest.setExecutedBy("dbladduwahetty@gmail.com");
		testPlanRequest.setTestSuiteFileName("abc/plans/SMOKETEST.xml");

		
		String       postUrl       = "http://localhost:8080/api/testPlan";// put in your url
		
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(gson.toJson(testPlanRequest));//gson.tojson() converts your pojo to json
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
	}
	
	public static void postTestResult() throws ClientProtocolException, IOException {
		
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();  
		Gson gson = gsonBuilder.create();
		
		TestResultRequest testResultRequest = new TestResultRequest();
		testResultRequest.setTestPlanId("TEP-53");
		testResultRequest.setExecutionStartTimestamp("2020-12-31 22:59:59");
		testResultRequest.setTestResultStatus("Passed");
		testResultRequest.setBaseURL("http://example.com/");
		testResultRequest.setBrowser("CHROME");
		testResultRequest.setTestDriver("WEB");
		testResultRequest.setExecutionMode("DEFAULT");
		testResultRequest.setExecutionCompletedTimestamp("2020-12-31 23:59:59");
		testResultRequest.setTestExecutionLog(gson.toJson(testResultRequest));
		
		String       postUrl       = "http://localhost:8080/api/testResult";
		
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(gson.toJson(testResultRequest));//gson.tojson() converts your pojo to json
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
		
	}
	
	public static void getTestPlan() {
		  try {

				URL url = new URL("http://localhost:8080/testPlan/tep1");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}

				conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  }
	}
}
