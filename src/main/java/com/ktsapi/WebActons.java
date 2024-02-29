package com.ktsapi;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.elements.AvtomatWebDriverWait;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.BrowserNavigation;
import com.ktsapi.elements.BrowserWindow;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.elements.FrameElement;
import com.ktsapi.elements.TargetLocatorFrame;
import com.ktsapi.elements.impl.BrowserNavigationImpl;
import com.ktsapi.exceptions.TestClassNotFoundException;
import com.ktsapi.exceptions.WebActionsExceptions;
import com.ktsapi.pagefactory.AvtomatPageFactory;

/**
 * 
 * @author bladduwahetty
 *
 *         This Defines the Browser Automation Framework(BAF) API
 */
public class WebActons {
	
	public static <C> C GetWebPage(Class<C> page) {		
		return newInstance().GetWebPage(page);
	}
	
	public static void OpenBrowser() {
		newInstance().OpenBrowser();		
	}
	
	public static WebDriver driver(){
		return newInstance().driver();
	}
	
	public static KandyTestWebDriverActions newInstance(){
		return KTestActonsHandler.webDriverActionsInstance();
	}
	
	
	public static <C> C getWebPage(Class<C> page) {		
		return newInstance().GetWebPage(page);
	}
	
	

// SeleniumCaller Define WebDriverActions --------------------------------------------------------------------------
	
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String GoTo(String url) {
		return newInstance().GoTo(url);
	}
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String GetNewWindow(String url) {
		return newInstance().GetNewWindow(url);
		
	}
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String GetNewTab(String url) {
		return newInstance().GetNewTab(url);
	}
	
	/**
	 * @return The URL of the page currently loaded in the browser
	 */
	public static String GetUrl() {
		return newInstance().GetUrl();
	}
	
	/**
	 *@return The title of the current page
	 */
	public static String GetTitle() {
		return newInstance().GetTitle();
	}
	
//	/**
//	 * @param selenium By Selector
//	 * @return WebElement
//	 */
//	public static WebElement $$(By seleniumSelector){
//		return newInstance().$$(seleniumSelector);
//	}
	
	/**
	 * @param selenium webElement
	 * @return BaseElement
	 */
	public static BaseWebElement $(WebElement webElement) {		
		return newInstance().$(webElement);
	}

	/**
	 * This will first invoke findElement, hence this should only look for present elements
	 * @param By locator of the element
	 * @return BaseWebElement
	 * @throws NoSuchElementException If no matching elements are found
	 */
	public static BaseWebElement $(By seleniumSelector) {
		return newInstance().$(seleniumSelector);
	}
	
	/**
	 * @return The source of the current page
	 */
	public String GetPageSource() {
		return newInstance().GetPageSource();
	}

	/**
	 * Close the current Browser window(tab,Popup, new window) quitting the browser if it's the last window currently open.
	 */
	public static void CloseBrowserWindow() {
		newInstance().CloseBrowserWindow();
	}

	/**
	 * Close Browser
	 */
	public static void CloseBrowser() {
		newInstance().CloseBrowser();
	}
	
	public static String CurrentWindowHandle() {
		return newInstance().CurrentWindowHandle();
	}
	
	public static Set<String> AllWindowHandles() {
		return newInstance().AllWindowHandles();
	}
	
	public static String ParentWindowHandle() {
		return newInstance().ParentWindowHandle();
	}
	

// Selenium WebElement defined Actions--------------------------------------------------------------------------
	
		public static void Clear(BaseWebElement element) {
			newInstance().Clear(element);
		}
		
		public static void Click(BaseWebElement element) {
			newInstance().Click(element);
		}
		
		public static void SendKeys(BaseWebElement element, CharSequence... keysToSend){
			newInstance().SendKeys(element, keysToSend);
		}
		
		public static void Submit(BaseWebElement element){
			newInstance().Submit(element);
		}
		
		public static String GetTagName(BaseWebElement element){
			return newInstance().GetTagName(element);
		}	
		
		public static String GetAttribute(BaseWebElement element, String attributeName){
			return newInstance().GetAttribute(element, attributeName);
		}
		
		public static boolean IsSelected(BaseWebElement element){
			return newInstance().IsSelected(element);
		}
		
		public static boolean IsEnabled(BaseWebElement element){
			return newInstance().IsEnabled(element);
		}
		public static String GetText(BaseWebElement element){
			return newInstance().GetText(element);
		}
//		public static EnhancedWebElement FindElement(BaseWebElement element, By by) {	
//			return newInstance().FindElement(element,by);
//		}
		public static boolean IsDisplayed(BaseWebElement element){
			return newInstance().IsDisplayed(element);
		}
		public static Point GetLocation(BaseWebElement element){
			return newInstance().GetLocation(element);
		}
		public static Dimension GetSize(BaseWebElement element){
			return newInstance().GetSize(element);
		}
		public static Rectangle GetRect(BaseWebElement element){
			return newInstance().GetRect(element);
		}
		public static String GetCssValue(BaseWebElement element,String propertyName){			
			try {
				return newInstance().GetCssValue(element,propertyName);
			}catch(Exception e) {
				throw new WebActionsExceptions(e.getMessage());
			}
		}
// End of Selenium WebElement Define Actions ---------------------------------------------------------------




	
	

// SeleniumCaller Define WebElementActions ------------------------------------------------------------------

	public static void Type(BaseWebElement element, CharSequence... keysToSend){
//		try {
//			newInstance().Type(element, keysToSend);
//		}catch(Exception e) {
//			throw new WebActionsExceptions(e.getMessage());
//		}		
		newInstance().Type(element, keysToSend);
	}
	
	public static void NativeClick(BaseWebElement element) {
		newInstance().NativeClick(element);
	}
	
	public static void Check(BaseWebElement element) {
		newInstance().Check(element);
	}
	
	public static void UnCheck(BaseWebElement element) {
		newInstance().UnCheck(element);
	}
	
	public static ComboBox ToCombobox(BaseWebElement element) {
		return newInstance().ToComboBox(element);
	}
	
	public static FrameElement ToFrameElement(BaseWebElement element) {
		return newInstance().ToFrameElement(element);
	}	
	
	// TODO : Need to implement
	public static EnhancedWebElement FindElement(By locator){		
		return newInstance().FindEnhancedWebElement(locator);
	}

	
	public static ExpectedConditions WaitUntil(BaseWebElement element, long timeOutInSeconds) {
		return newInstance().WaitUntil(element, timeOutInSeconds);
	}
	
	public static void dragAndDropTo(BaseWebElement fromElement, BaseWebElement toElement) {
		newInstance().DragAndDropTo(fromElement,toElement);
	}
	
	public static int getElementCount(BaseWebElement element) {
		return newInstance().getElementCount(element);
	}
// End Of SeleniumCaller Define Elements -------------------------------------------------------------------


	
	// Web Driver Navigation -------------------------------------------------------------------

	public static BrowserNavigation BrowserNavigate() {
		return newInstance().BrowserNavigate();
	}

	public static TargetLocatorFrame SwitchTo() {
		return newInstance().SwitchTo();
	}
	
	/** 
	 * @param windowHandle.
	 */
	public static void SwitchToWindowOrTab(String windowHandle) {
		newInstance().SwitchToWindowOrTab(windowHandle);
	}
	
	// SeleniumCaller Define Actions --------------------------------------------------------------------------
	
		public static <T> void WaitUntil(ExpectedCondition<T> condition,long timeOutInSeconds){
			newInstance().WaitUntil(condition,timeOutInSeconds);
		}
		
//		public static <C> C getPage(Class<C> page) {
//			//BasePage obj = new BasePage(driver);
//			return newInstance().GetPage(page);
//			
//			//return Object;
//		}
		
		public static void HandleBrowserWindow(BrowserWindow browserWindow,long timeOutInSeconds) {
			newInstance().HandleBrowserWindow(browserWindow,timeOutInSeconds);
			
		}
		
		public static DriverTimeOuts ManageTimeouts() {
			return newInstance().ManageTimeouts();
		}
		
		public static org.openqa.selenium.Alert Alert() {
			return newInstance().Alert();
		}

	// End of SeleniumCaller Define Actions -------------------------------------------------------------------

}
