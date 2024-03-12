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
import com.ktsapi.utils.AvtomatUtils;

import io.appium.java_client.AppiumBy;

@TestConfiguration(
		testDriver = TestDriver.MOBILE_ANDROID, // Mandatory
		mobileApp ="Android-NativeDemoApp-0.4.0.apk", //"Android.SauceLabs.Mobile.Sample.app.2.7.1.apk", //"Android-NativeDemoApp-0.4.0.apk",
		mobileDeviceName = "Pixel_2_XL_API_33",//"pixel_2_xl",
		browser=Browsers.CHROME,
		mobileCapabilitiesFileName = "Android-NativeDemoApp-0.4.0.json"
)

public class TestMain {

	@Test
	public void testMothod() {	
		System.out.println("###:"+ AvtomatUtils.getOS()); 
		System.out.println("###:"+TestInitializr.getSysConfigObj().getOs().getMac().getTagName());
	}

}
