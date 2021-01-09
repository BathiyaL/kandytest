package com.ktsapi.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public interface BaseWebElement {

	public boolean isVisible();
	
	public WebElement asWebelement();
	
	public By getByLocator();
	
	public String getFieldName();
	
	public EnhancedWebElementLocator getEnhancedWebElementLocator();
}
