package com.ktsapi.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;

import com.ktsapi.annotation.ActionImplements;
import com.ktsapi.annotation.OverrideAction;

@ActionImplements(name=Navigation.class)
public interface WebDriverNavigationActions {
	
	@OverrideAction(name="back()")
	void Back();

}
