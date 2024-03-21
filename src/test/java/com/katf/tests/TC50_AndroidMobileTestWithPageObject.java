package com.katf.tests;

import static com.ktsapi.MobileActions.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.katf.pageobjects.AndroidDemoPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
	testDriver = TestDriver.MOBILE_ANDROID, // Mandatory
	mobileApp = "General-Store.apk",
	mobileDeviceName = "Pixel_3a_API_33_arm64-v8a",//"pixel_2_xl",Pixel_2_XL_API_33
	browser=Browsers.CHROME
	//mobileCapabilitiesFileName = "General-Store.apk.json"
)
public class TC50_AndroidMobileTestWithPageObject {
	
	final String PRODUCT_NAME = "Jordan 6 Rings";
	
	@BeforeTest
	public void goSite() {	
		OpenMobileApp();
		Assert.assertEquals(TestInitializr.getTestConfiguration().getMobileApp(), "General-Store.apk","App Name mismatch");
		Assert.assertEquals(TestInitializr.getTestConfiguration().getMobileDeviceName(), "Pixel_3a_API_33_arm64-v8a","Device Name(Emulator) mismatch");
	 }

	@Test
	public void testMothod() {	
		
		AndroidDemoPage page = getAndroidPage(AndroidDemoPage.class);
		page.name.type("T", "o","m");
		String name = mobileDriver().findElement(page.name.getByLocator()).getText();
		Assert.assertEquals(name, "Tom","Name mismatch");
		page.name.typeWithLocatorParms("Sam");
		name = mobileDriver().findElement(page.name.getByLocator()).getText();
		Assert.assertEquals(name, "Sam","Name mismatch");
		
		//hideKeyboard();
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
