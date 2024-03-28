package com.ktsapi.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.elements.FrameElement;

//org.openqa.selenium.WebElement
public interface EnhancedWebElementActions extends SeleniumWebElementActions{

	//ABotWebElement........................
	void type(BaseWebElement element, CharSequence... keysToSend);
	void check(BaseWebElement element);
	void unCheck(BaseWebElement element);
	void click(BaseWebElement element);
	//void Select(); add this element
	void dragAndDropTo(BaseWebElement fromElement,BaseWebElement toElement);
	int getElementCount(BaseWebElement element);
	
	ComboBox toComboBox(BaseWebElement element);
	FrameElement toFrameElement(BaseWebElement element);
		
	
	EnhancedWebElement findEnhancedWebElement(By locator);	
	//ABot element type converters
	//WebElement $$(By seleniumSelector);
	WebElement $$(BaseWebElement element);
	BaseWebElement $(WebElement webElement);
	BaseWebElement $(By seleniumSelector);
	
	ExpectedConditions waitUntil(BaseWebElement element, long timeOutInSeconds);

	<T> void waitUntil(ExpectedCondition<T> condition,long timeOutInSeconds);	
	
}
