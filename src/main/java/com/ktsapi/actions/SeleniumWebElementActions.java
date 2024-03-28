package com.ktsapi.actions;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import com.ktsapi.annotation.ActionImplements;
import com.ktsapi.elements.BaseWebElement;

@ActionImplements(name=WebElement.class)
public interface SeleniumWebElementActions {

	//SeleniumWebElement.....................
		
		void nativeClick(BaseWebElement element);
		void sendKeys(BaseWebElement element, CharSequence... keysToSend);
		void clear(BaseWebElement element);
		void submit(BaseWebElement element);
		String getTagName(BaseWebElement element);
		String getAttribute(BaseWebElement element, String attributeName);
		boolean isSelected(BaseWebElement element);
		boolean isEnabled(BaseWebElement element);
		String getText(BaseWebElement element);
		boolean isDisplayed(BaseWebElement element);
		Point getLocation(BaseWebElement element);
		Dimension getSize(BaseWebElement element);
		Rectangle getRect(BaseWebElement element);
		String getCssValue(BaseWebElement element,String propertyName);

	//---------------------------------------
}
