package com.katf.tests;

import static com.ktsapi.CommonActions.print;
import static com.ktsapi.MobileActions.OpenMobileApp;
import static com.ktsapi.MobileActions.mobileDriver;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.TestDriver;

import io.appium.java_client.AppiumBy;

@TestConfiguration(
		testDriver = TestDriver.MOBILE_ANDROID, // Mandatory
		mobileApp = "General-Store.apk",
		mobileDeviceName = "pixel_2_xl"
)
public class TC50_AndroidMobileTestDemo {
	
	final String PRODUCT_NAME = "Jordan 6 Rings";
	
	@BeforeTest
	public void goSite() {	
		
		OpenMobileApp();
		print(TestInitializr.getTestConfiguration().getMobileApp());
		print(TestInitializr.getTestConfiguration().getMobileDeviceName());
//	      OpenBrowser();
//	      
//	      GoTo(TestInitializr.getTestConfiguration().getBaseUrl());
//	      print(GetTitle());
	 }

	@Test
	public void testMothod() {	
		mobileDriver().findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Don Bat");
		mobileDriver().hideKeyboard();
		mobileDriver().findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
		mobileDriver().findElement(By.id("android:id/text1")).click();
		mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));"));
		mobileDriver().findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
		mobileDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+PRODUCT_NAME+"\"));"));
		print("Test Pass");
	}

}
