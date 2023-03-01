package com.ktsapi.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public interface BaseMobileElement {

	public By getByLocator();

	WebElement asWebelement();

	String getFieldName();

	EnhancedMobileElementLocator getEnhancedMobileElementLocator();
}
