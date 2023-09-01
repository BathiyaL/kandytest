package com.katf.element.test;

import static com.ktsapi.CommonActions.pause;
import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;
import static com.ktsapi.WebActons.getWebPage;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.pageobject.SampleFBPage2;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		//browserVersion="74",
		browserOptions = {"--start-maximized","--ignore-certificate-errors"}
)
public class SPSampleTest {
	@BeforeTest
	public void beforeTest1() {
	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in beforeTest1");
	      //String chromeOptions[] = {"--start-maximized","--ignore-certificate-errors"};
	 }
	
	@BeforeTest
	public void beforeTest2() {
	      System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> in beforeTest2");
	      //String chromeOptions[] = {"--start-maximized","--ignore-certificate-errors"};
	      //assertEquals(true, false);
	 }
	
    @Test
    public void testFirst(){
    	OpenBrowser();
    	GoTo("https://www.facebook.com/");
    	SampleFBPage2 fbPage = getWebPage(SampleFBPage2.class); //WebActons.getPage(SampleFBPage2.class);
    	fbPage.username.type("Test11111");
    	pause(3);
    }
}
