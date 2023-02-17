package com.ktsapi;

import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.KandyTestMobileDriverActions;

import io.appium.java_client.android.AndroidDriver;

public class MobileActions {

	public static void OpenMobileApp() {
		newInstance().OpenMobileApp();
	}
	
	public static AndroidDriver mobileDriver(){
		return newInstance().mobileDriver();
	}
	
	public static KandyTestMobileDriverActions newInstance(){
		return ABotActonsHandler.mobileDriverActionsInstance();
	}
	
}
