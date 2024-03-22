package com.katf.tests;

import static com.ktsapi.MobileActions.*;
import static com.ktsapi.CommonActions.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

import io.appium.java_client.AppiumBy;

@TestConfiguration(
	testDriver = TestDriver.MOBILE_ANDROID,
	mobileApp = "General-Store.apk",
	mobileDeviceName = "Pixel_3a_API_33_arm64-v8a",
	browser=Browsers.CHROME
	//mobileCapabilitiesFileName = "General-Store.apk.json"
)
public class TC51_AndroidMobileTestWithoutPageObject {
	
	final String PRODUCT_NAME = "Jordan 6 Rings";
	
	@BeforeTest
	public void goSite() {	
		OpenMobileApp();
		Assert.assertEquals(TestInitializr.getTestConfiguration().getMobileApp(), "General-Store.apk","App Name mismatch");
		Assert.assertEquals(TestInitializr.getTestConfiguration().getMobileDeviceName(), "Pixel_3a_API_33_arm64-v8a","Device Name(Emulator) mismatch");
	 }

	@Test
	public void testMothod() {	
		mobileDriver().findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Don Bat");
		pause(15);
		//mobileDriver().hideKeyboard();
		mobileDriver().findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
		mobileDriver().findElement(By.id("android:id/text1")).click();
		mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));"));
		mobileDriver().findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
		
		mobileDriver().findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
		mobileDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+PRODUCT_NAME+"\"));"));

	}

}

