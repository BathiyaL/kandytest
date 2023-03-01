package com.ktsapi.mobile.actions;

import com.ktsapi.annotation.ActionImplements;

import io.appium.java_client.android.AndroidDriver;

@ActionImplements(name=AndroidDriver.class)
public interface AndroidDriverActions {

	void hideKeyboard();
}
