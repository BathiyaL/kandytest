package com.ktsapi;

import com.ktsapi.mobile.KAndroidPageFactory;
import com.ktsapi.mobile.actions.KandyTestMobileDriverActions;

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
	
	public static <C> C getAndroidPage(Class<C> page) {
		return (C) KAndroidPageFactory.getWebPage(page);
	}

	// AndroidDriverActions 
	
	public static void hideKeyboard() {
		newInstance().hideKeyboard();
	}
}
