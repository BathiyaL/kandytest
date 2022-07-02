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
		
		void NativeClick(BaseWebElement element);
		void SendKeys(BaseWebElement element, CharSequence... keysToSend);
		void Clear(BaseWebElement element);
		void Submit(BaseWebElement element);
		String GetTagName(BaseWebElement element);
		String GetAttribute(BaseWebElement element, String attributeName);
		boolean IsSelected(BaseWebElement element);
		boolean IsEnabled(BaseWebElement element);
		String GetText(BaseWebElement element);
		//EnhancedWebElement FindElement(BaseWebElement element,By by);
		boolean IsDisplayed(BaseWebElement element);
		Point GetLocation(BaseWebElement element);
		Dimension GetSize(BaseWebElement element);
		Rectangle GetRect(BaseWebElement element);
		String GetCssValue(BaseWebElement element,String propertyName);

	//---------------------------------------
}
