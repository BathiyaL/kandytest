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
		mobileApp = "General-Store.apk",
		mobileDeviceName = "Pixel_2_XL_API_33",//"pixel_2_xl",
		browser=Browsers.CHROME
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
		
		AndroidDemoPage page = getAndroidPage(AndroidDemoPage.class);
		print(">>>>>>>>>>>>>>>>>>>>>>>>" + page.name.getByLocator());
		//page.name.type("Don Bat");
		//page.name.type("Don Bat", "AAA");
		page.name.type("A", "B","C");
		page.name.typeWithLocatorParms("x", "y","z");
		hideKeyboard();
		page.genderFemale.click();
		page.countryDropdown.click();
		
		//page.argentina.scrollToElement();
		page.argentina.click(); // we have used uiautomator selector with scrollIntoView, hence this will scroll to the element and click
		
		page.letsShop.click();
		page.dynamicProduct.click(PRODUCT_NAME);
		
		//mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+PRODUCT_NAME+"\"));"));
		
		
//		mobileDriver().findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Don Bat");
//		mobileDriver().hideKeyboard();
//		mobileDriver().findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
//		mobileDriver().findElement(By.id("android:id/text1")).click();
//		mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));"));
//		mobileDriver().findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
		
		//mobileDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		//mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+PRODUCT_NAME+"\"));"));
		//print("Test Pass"); 
//
//		By by = AppiumBy.id(""); 
//		AndroidFindBy
		
		
		// dynamic elements
		// page.element.setLocatorPrameters().click();
		



		
	}

}
