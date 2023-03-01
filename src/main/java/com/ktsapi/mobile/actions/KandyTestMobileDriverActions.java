package com.ktsapi.mobile.actions;

import io.appium.java_client.android.AndroidDriver;

public interface KandyTestMobileDriverActions extends AndroidDriverActions{

	void OpenMobileApp();
	
	AndroidDriver mobileDriver();
	
}
