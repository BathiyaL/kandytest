package com.ktsapi.elements.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public class BaseWebElementImpl implements BaseWebElement {

	protected WebElement element;
	protected By byLocator;
	protected String fieldName; // if element decorate from page object, file name is the name of the element variable name
	protected EnhancedWebElementLocator enhancedWebElementLocator;

	public BaseWebElementImpl(final WebElement el) {
		this.element = el;
	}
	
	//protected WebElement webelement;
	
	public BaseWebElementImpl(final WebElement webelement, final By byLocator, final String fieldName) {
		this.element = webelement;
		this.byLocator = byLocator;
		this.fieldName = fieldName;
		//this.element = new Element(webelement,byLocator);
	}

	public BaseWebElementImpl(final WebElement webelement, EnhancedWebElementLocator enhancedWebElementLocator) {
		this.element = webelement;
		this.byLocator = enhancedWebElementLocator.getTarget();
		this.fieldName = enhancedWebElementLocator.getFieldName();
		this.enhancedWebElementLocator = enhancedWebElementLocator;
		//this.element = new Element(webelement,byLocator); (WebElement webelement, EnhancedWebElementLocator enhancedWebElementLocator)
	}
	
	// this is not working properly, seems its not switch to iframse when calling from this implimantation 
	// consider remove this
	@Override
	public boolean isVisible() {
		return element.isDisplayed();
	}

	@Override
	public WebElement asWebelement() {		
		return element;
	}

	@Override
	public By getByLocator() {		
		return byLocator;
	}

	@Override
	public String getFieldName() {		
		return fieldName;
	}

	@Override
	public EnhancedWebElementLocator getEnhancedWebElementLocator() {		
		return enhancedWebElementLocator;
	}
	
	

	// public abstract ExpectedCondition<WebElement> isReady();

}
