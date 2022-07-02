package com.ktsapi.elements.impl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.ktsapi.WebActons;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.elements.FrameElement;
import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public class EnhancedWebElementImpl extends BaseWebElementImpl implements EnhancedWebElement{

	protected EnhancedWebElementImpl(WebElement element) {
		super(element);		
	}
	
	public EnhancedWebElementImpl(WebElement webelement, By byLocator, String fieldName) {
		super(webelement, byLocator, fieldName);		
	}
	
	public EnhancedWebElementImpl(WebElement webelement, EnhancedWebElementLocator enhancedWebElementLocator) {
		super(webelement, enhancedWebElementLocator);		
	}

	// ABot Define Actions Section .................................................
	@Override
	public void type(CharSequence... keysToSend) {
		WebActons.Type(this, keysToSend);		
	}
	
	@Override
	public void nativeClick() {
		WebActons.NativeClick(this);		
	}
	
	// End of ABot Define Actions ..................................................

	
	// Selenium WebElement defined Actions .........................................
	@Override
	public void clear() {
		WebActons.Clear(this);		
	}
	
	@Override
	public void check() {
		WebActons.Check(this);		
	}
	@Override
	public void unCheck() {
		WebActons.UnCheck(this);		
	}

	@Override
	public void click() {
		WebActons.Click(this);		
		
	}

	@Override
	public void submit() {
		WebActons.Submit(this);		
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		WebActons.SendKeys(this, keysToSend);
	}

	@Override
	public String getTagName() {		
		return WebActons.GetTagName(this);
	}

	@Override
	public String getAttribute(String attributeName) {
		return WebActons.GetAttribute(this,attributeName);
	}

	@Override
	public boolean isSelected() {
		return WebActons.IsSelected(this);
	}

	@Override
	public boolean isEnabled() {
		return WebActons.IsEnabled(this);
	}

	@Override
	public String getText() {
		return WebActons.GetText(this);
	}

	@Override
	public List<EnhancedWebElement> findElements(By by) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnhancedWebElement findElement(By by) {
		KandyTestWebDriverActionsImpl actionImple = new KandyTestWebDriverActionsImpl();
		//return actionImple.FindElement(this, by);
		//return WebActons.FindElement(this, by);
		return actionImple.FindEnhancedWebElement(by);
	}

	@Override
	public boolean isDisplayed() {
		return WebActons.IsDisplayed(this);
	}

	@Override
	public Point getLocation() {
		return WebActons.GetLocation(this);
	}

	@Override
	public Dimension getSize() {
		return WebActons.GetSize(this);
	}

	@Override
	public Rectangle getRect() {
		return WebActons.GetRect(this);
	}

	@Override
	public String getCssValue(String propertyName) {
		return WebActons.GetCssValue(this,propertyName);
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComboBox toComboBox() {
		return WebActons.ToCombobox(this);
	}

	@Override
	public FrameElement toFrameElement() {		
		return  WebActons.ToFrameElement(this);
	}

	@Override
	public ExpectedConditions waitUntil(long timeOutInSeconds) {
		return  WebActons.WaitUntil(this, timeOutInSeconds);
	}

	@Override
	public void dragAndDropTo(BaseWebElement toElement) {
		WebActons.dragAndDropTo(this,toElement);
		
	}

	@Override
	public int getElementCount() {
		return WebActons.getElementCount(this);
	}




}
