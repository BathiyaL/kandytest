package com.ktsapi.elements;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.ktsapi.annotation.ActionImplements;

/*
 * this will wrap actions of Selenium WebElement
 */

@ActionImplements(name=WebElement.class)
public interface SeleniumWebElement {

	/*
	 *  same as selenium click, sine click is wrap with more control implementation here we used different name
	 */
	  void nativeClick();

	  void submit();


	  void sendKeys(CharSequence... keysToSend);


	  void clear();


	  String getTagName();


	  String getAttribute(String name);


	  boolean isSelected();


	  boolean isEnabled();


	  String getText();


	  boolean isDisplayed();


	  Point getLocation();


	  Dimension getSize();


	  Rectangle getRect();


	  String getCssValue(String propertyName);
	  
	  //-----------------------------------------TakesScreenshot---------------------------------------

	  <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException;
}
