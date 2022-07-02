package com.ktsapi.elements;

import java.awt.Component.BaselineResizeBehavior;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.ktsapi.elements.impl.ExpectedConditionsImpl;

public interface EnhancedWebElement extends BaseWebElement,SeleniumWebElement{

	/**
	 * This action clear Text element value before type on it
	 *
	 * @param value text value to type on the element
	 *            
	 */
	void type(CharSequence... keysToSend);	
	
	/**
	 * Use this action on input elements such as Checkbox or Radio buttons.
	 * If isSelected status of the element is false only this will check on the element.
	 * Element other than chekcbox or radio and isSelected state is false can receive a click action.
	 */
	void check();
	void unCheck();
	
	  /**
	   * Click this element. If this throw ElementClickInterceptedException saying
	   * element is not clickable at point...., this will try with javascript click
	   * If you don't want this logic to happen you can use nativeClick(), which is same as 
	   * selenium click.
	   */
	void click();
	
	/**
	 * This is applicable on for element which  which has select tag name
	 * If element is not a select type element UnexpectedWebElementException will be thrown.
	 * @throws UnexpectedTagNameException when element is not a SELECT
	 */
	ComboBox toComboBox();
	
	FrameElement toFrameElement();
	
	ExpectedConditions waitUntil(long timeOutInSeconds);
	
	void dragAndDropTo(BaseWebElement toElement);
	
	int getElementCount();
	
/*
 * Need to decide usage of this with selenium findelements
 */
	List<EnhancedWebElement> findElements(By by);	
	EnhancedWebElement findElement(By by);

}
