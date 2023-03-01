package com.ktsapi.mobile;

import static com.ktsapi.MobileActions.mobileDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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

	@Override
	public void click() {
		mobileDriver().findElement(getByLocator()).click();
		
	}

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
	public void click(String ... args) {
		System.out.println("====================== : " + getByLocator());
		String locatorStr = getByLocator().toString().split(":")[1].trim();
		String locatorStrategy = getByLocator().toString().split(":")[0].trim();
		System.out.println("====================== : " + locatorStr.formatted(args));
		
		// TODO : check for %S, and also count tally with args count to verify its valid dynamic elemetn and throw a warning
		mobileDriver().findElement(consructByLocator(locatorStrategy, locatorStr.formatted(args))).click();
		
		
		
	}
	
	private By consructByLocator(String locatorStrategy, String locatorString) {
		
		if(locatorStrategy.equals("AppiumBy.androidUIAutomator")) {
			return AppiumBy.androidUIAutomator(locatorString);
		}
		return byLocator;
		
	}

}
