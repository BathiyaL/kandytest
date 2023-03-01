package com.ktsapi.mobile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public class BaseMobileElementImpl implements BaseMobileElement{

	
	protected WebElement element;
	protected By byLocator;
	protected String fieldName; // if element decorate from page object, file name is the name of the element variable name
	protected EnhancedMobileElementLocator enhancedMobileElementLocator;
	
	public BaseMobileElementImpl(final WebElement webelement, EnhancedMobileElementLocator enhancedMobileElementLocator) {
		
		this.byLocator = enhancedMobileElementLocator.getTarget();
		this.fieldName = enhancedMobileElementLocator.getFieldName();
		this.enhancedMobileElementLocator = enhancedMobileElementLocator;
		//this.element = new Element(webelement,byLocator); (WebElement webelement, EnhancedWebElementLocator enhancedWebElementLocator)
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
	public EnhancedMobileElementLocator getEnhancedMobileElementLocator() {
		return enhancedMobileElementLocator;
	}

	

}
