package com.ktsapi.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.elements.BaseWebElement;

public interface AvtomatElementFactory {
	public <E extends BaseWebElement> E create(Class<E> containerClass, WebElement wrappedElement, By byLoctor, String fieldName);
	public <E extends BaseWebElement> E create(Class<E> containerClass, WebElement wrappedElement, EnhancedWebElementLocator enhancedWebElementLocator);
}
