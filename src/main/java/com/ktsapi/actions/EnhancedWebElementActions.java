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
//	void Type(WebElement element, String Value); // sendKeys
	
	
	//ABotWebElement........................
	void Type(BaseWebElement element, CharSequence... keysToSend);
	void Check(BaseWebElement element);
	void UnCheck(BaseWebElement element);
	void Click(BaseWebElement element);
	//void Select(); add this element
	void DragAndDropTo(BaseWebElement fromElement,BaseWebElement toElement);
	int getElementCount(BaseWebElement element);
	
	ComboBox ToComboBox(BaseWebElement element);
	FrameElement ToFrameElement(BaseWebElement element);
		
	
	EnhancedWebElement FindEnhancedWebElement(By locator);	
	//ABot element type converters
	//WebElement $$(By seleniumSelector);
	WebElement $$(BaseWebElement element);
	BaseWebElement $(WebElement webElement);
	BaseWebElement $(By seleniumSelector);
	
	ExpectedConditions WaitUntil(BaseWebElement element, long timeOutInSeconds);

	<T> void WaitUntil(ExpectedCondition<T> condition,long timeOutInSeconds);	
	
}
