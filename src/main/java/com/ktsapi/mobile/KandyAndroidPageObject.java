package com.ktsapi.mobile;

import io.appium.java_client.android.AndroidDriver;

public class KandyAndroidPageObject<B extends KandyAndroidPageObject<B>> {

	private AndroidDriver driver;
	
	public KandyAndroidPageObject(AndroidDriver driver) {
		this.driver = driver;
	}
    public AndroidDriver getDriver() {
    	return driver;
    }
    
}
