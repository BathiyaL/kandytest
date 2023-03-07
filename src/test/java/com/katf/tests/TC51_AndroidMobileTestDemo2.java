package com.katf.tests;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.MobileActions.*;
import static com.ktsapi.MobileActions.mobileDriver;


import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.katf.pageobjects.AndroidDemoPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

import io.appium.java_client.AppiumBy;

@TestConfiguration(
		testDriver = TestDriver.MOBILE_ANDROID, // Mandatory
		mobileApp = "Android-NativeDemoApp-0.4.0.apk",
		mobileDeviceName = "Pixel_2_XL_API_33",//"pixel_2_xl",
		browser=Browsers.CHROME
)
public class TC51_AndroidMobileTestDemo2 {
	
	final String PRODUCT_NAME = "Jordan 6 Rings";
	
	@BeforeTest
	public void goSite() {			
		OpenMobileApp();
		print(TestInitializr.getTestConfiguration().getMobileApp());
		print(TestInitializr.getTestConfiguration().getMobileDeviceName());
	 }

	@Test
	public void testMothod() {	

		print(">>>>>>>>>>>>>>>>>>>>>>>> hello new fella");
		

	}

}
