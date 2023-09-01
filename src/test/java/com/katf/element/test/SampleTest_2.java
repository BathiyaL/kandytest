package com.katf.element.test;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.WebActons.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		//browserVersion="74",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class SampleTest_2 {

	@BeforeTest
	public void beforeTest1() {
	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_beforeTest1");
	 }
	
	@BeforeTest
	public void beforeTest2() {
	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_beforeTest2");
	 }

	
    @Test
    public void testFirst(){
    	OpenBrowser();
    	print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_Test : 1");
    	print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_Test : 2");
    	print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_Test : 3");
    	print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_Test : 4");
    	print(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_Test : 5");
    }
    
//    @AfterTest
//	public void afterTest1() {
//	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_afterTest1");
//	 }
//    
//    @AfterTest
//	public void afterTest2() {
//	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in SampleTest_2_afterTest2");
//	 }
}
