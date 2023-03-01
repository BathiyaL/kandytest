package com.ktsapi.mobile.actions;

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
	@Override
	public void hideKeyboard() {
		mobileDriver().hideKeyboard();		
	}

}
