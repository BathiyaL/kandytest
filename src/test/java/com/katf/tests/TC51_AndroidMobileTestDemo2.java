package com.katf.tests;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.MobileActions.*;
import static com.ktsapi.MobileActions.mobileDriver;
import static org.assertj.core.api.Assertions.assertThat;

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
		mobileApp ="Android-NativeDemoApp-0.4.0.apk", //"Android.SauceLabs.Mobile.Sample.app.2.7.1.apk", //"Android-NativeDemoApp-0.4.0.apk",
		mobileDeviceName = "Pixel_2_XL_API_33",//"pixel_2_xl",
		browser=Browsers.CHROME
)
public class TC51_AndroidMobileTestDemo2 {
	
	final String EXPECTEDLOGGED_IN_MSG = "You are logged in!";
	
	@BeforeTest
	public void goSite() {			
		OpenMobileApp();
		print(TestInitializr.getTestConfiguration().getMobileApp());
		print(TestInitializr.getTestConfiguration().getMobileDeviceName());
	 }

	@Test
	public void testMothod() {	

		print(">>>>>>>>>>>>>>>>>>>>>>>> hello new fella");
		
		mobileDriver().findElement(AppiumBy.accessibilityId("Home")).click();
		//mobileDriver().findElement(By.xpath("//android.widget.Button[@content-desc=\"Home\"]")).click();
		
		mobileDriver().findElement(By.xpath("//android.widget.Button[@content-desc=\"Login\"]")).click();
		
		mobileDriver().findElement(AppiumBy.accessibilityId("input-email")).sendKeys("bat@mobiletest.com");
		mobileDriver().findElement(AppiumBy.accessibilityId("input-password")).sendKeys("passwordtxt");
		
		mobileDriver().findElement(AppiumBy.accessibilityId("button-LOGIN")).click();
		
		String loggedInMsg = mobileDriver().findElement(AppiumBy.id("android:id/message")).getText();
		assertThat(EXPECTEDLOGGED_IN_MSG).isEqualTo(loggedInMsg);
		
		System.out.print("======================= :: " + loggedInMsg);
		mobileDriver().findElement(By.id("android:id/button1")).click();
		
		//mobileDriver().quit();
		
	} 

}
