package com.ktsapi.actions.core;

import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.KandyTestMobileDriverActions;
import com.ktsapi.core.TestInitializr;

import io.appium.java_client.android.AndroidDriver;

public class KandyTestMobileDriverActionsImpl implements KandyTestMobileDriverActions{

	public AndroidDriver mobileDriver(){			
		return TestInitializr.getMobileDriver();
	}
	@Override
	public void OpenMobileApp() {
		mobileDriver();
		
	}

}
