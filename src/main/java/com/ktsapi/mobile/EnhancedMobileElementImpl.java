package com.ktsapi.mobile;

import static com.ktsapi.MobileActions.mobileDriver;

import java.util.MissingFormatArgumentException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.exceptions.InvalidLocatorException;
import com.ktsapi.pagefactory.EnhancedWebElementLocator;

import io.appium.java_client.AppiumBy;

public class EnhancedMobileElementImpl extends BaseMobileElementImpl implements EnhancedMobileElement{

	protected WebElement element;

	
//	public EnhancedMobileElementImpl(final WebElement el) {
//		this.element = el;
//	}
	
	public EnhancedMobileElementImpl(final WebElement webelement, EnhancedMobileElementLocator enhancedMobileElementLocator) {
		super(webelement, enhancedMobileElementLocator);	
		this.element = webelement;
 
	}
	
	@Override
	public void type(CharSequence... keysToSend) {
		mobileDriver().findElement(getByLocator()).sendKeys(keysToSend);	
	}

//	@Override
//	public void click() {
//		mobileDriver().findElement(getByLocator()).click();
//		
//	}

	@Override
	public void scrollToElement() {
		
		if(getByLocator().toString().contains("AppiumBy.androidUIAutomator")) {
			// TODO : Further we can check for new UiScrollable(new UiSelector()).scrollIntoView, can log a waring
			mobileDriver().findElement(getByLocator());
		} else {
			System.out.println("WARNING : element has not used android uiAutomator locator strategy, hence cannot scroll"); // TODO:move to a log
		}
	}

	@Override
	public void click(String... locatorParams) {
		
		if(locatorParams.length==0) {
			mobileDriver().findElement(getByLocator()).click();
		}else {
			mobileDriver().findElement(consructByLocator(getLocatoreStrategy(), getFormattedLocator(locatorParams))).click();
		}
	}
	
	private String getFormattedLocator(String... locatorParams) {
		try {
			return getLocatoreString().formatted(locatorParams);
		}catch(MissingFormatArgumentException e) {
			throw new InvalidLocatorException("Make sure correct number of argmuments are passed for format specifier %s for element " + getFieldName());
		}
	}

	private By consructByLocator(String locatorStrategy, String locatorString) {
		
		if(locatorStrategy.equals("AppiumBy.androidUIAutomator")) {
			return AppiumBy.androidUIAutomator(locatorString);
		}
		return byLocator;
		
	}

	@Override
	public void typeWithLocatorParms(String textToType, String... locatorParams) {
		if(locatorParams.length==0) {
			mobileDriver().findElement(getByLocator()).sendKeys(textToType);
		}else {
			mobileDriver().findElement(consructByLocator(getLocatoreStrategy(), getFormattedLocator(locatorParams))).sendKeys(textToType);
		}
		
	}

}
