package com.katf.pageobjects;

import org.openqa.selenium.WebElement;

import com.ktsapi.mobile.EnhancedMobileElement;
import com.ktsapi.mobile.KandyAndroidPageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.HowToUseLocators;
import io.appium.java_client.pagefactory.LocatorGroupStrategy;

public class AndroidDemoPage extends KandyAndroidPageObject<AndroidDemoPage> {

	public AndroidDemoPage(AndroidDriver driver) {
		super(driver);
	}
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField",xpath = "Hello world")
	public EnhancedMobileElement name;
	
	// this option is working
//	@HowToUseLocators(androidAutomation = LocatorGroupStrategy.ALL_POSSIBLE)
//	@AndroidFindBy(className = "com.androidsample.generalstore:id/nameField1", priority = 1)
//	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField", priority = 2)
//	public WebElement nameField;
	
	// outdated , removed in latest appium vesons
//	@AndroidFindBys({
//	    @AndroidBy(id = "com.androidsample.generalstore:id/nameField2", priority = 1),
//	    @AndroidBy(id = "com.androidsample.generalstore:id/nameField", priority = 2)})
//	public AndroidElement  nameField;
	
	@AndroidFindBy(xpath="//android.widget.RadioButton[@text='Female']")
	public EnhancedMobileElement genderFemale;
	
	@AndroidFindBy(id="android:id/text1")
	public EnhancedMobileElement countryDropdown;
	
	
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField",xpath = "Hello world")
	public WebElement webelm;
	
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));")
	public EnhancedMobileElement argentina;
	
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	public EnhancedMobileElement letsShop;
	
	//@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"));")
	@AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"\"));")
	public EnhancedMobileElement dynamicProduct;
	
	
	
	public void selectCountry(String country) {
		
	}
	
}
