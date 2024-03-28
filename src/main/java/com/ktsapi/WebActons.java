package com.ktsapi;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.BrowserNavigation;
import com.ktsapi.elements.BrowserWindow;
import com.ktsapi.elements.ComboBox;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.elements.FrameElement;
import com.ktsapi.elements.TargetLocatorFrame;
import com.ktsapi.exceptions.WebActionsExceptions;

/**
 * 
 * @author bladduwahetty
 *
 *         This Defines the Browser Automation Framework(BAF) API
 */
public class WebActons {
	
	public static <C> C getWebPage(Class<C> page) {		
		return newInstance().getWebPage(page);
	}
	
	public static void openBrowser() {
		newInstance().openBrowser();		
	}
	
	public static WebDriver webDriver(){
		return newInstance().WebDriver();
	}
	
	private static KandyTestWebDriverActions newInstance(){
		return KTestActonsHandler.webDriverActionsInstance();
	}
	

// SeleniumCaller Define WebDriverActions --------------------------------------------------------------------------
	
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String goTo(String url) {
		return newInstance().goTo(url);
	}
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String getNewWindow(String url) {
		return newInstance().getNewWindow(url);
		
	}
	
	/** 
	 * @param url The URL to load.
	 * @return current window handle
	 */
	public static String getNewTab(String url) {
		return newInstance().getNewTab(url);
	}
	
	/**
	 * @return The URL of the page currently loaded in the browser
	 */
	public static String getUrl() {
		return newInstance().getUrl();
	}
	
	/**
	 *@return The title of the current page
	 */
	public static String getTitle() {
		return newInstance().getTitle();
	}

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
	public String getPageSource() {
		return newInstance().getPageSource();
	}

	/**
	 * Close the current Browser window(tab,Popup, new window) quitting the browser if it's the last window currently open.
	 */
	public static void closeBrowserWindow() {
		newInstance().closeBrowserWindow();
	}

	/**
	 * Close Browser
	 */
	public static void closeBrowser() {
		newInstance().closeBrowser();
	}
	
	public static String currentWindowHandle() {
		return newInstance().currentWindowHandle();
	}
	
	public static Set<String> allWindowHandles() {
		return newInstance().allWindowHandles();
	}
	
	public static String parentWindowHandle() {
		return newInstance().parentWindowHandle();
	}
	

// Selenium WebElement defined Actions--------------------------------------------------------------------------
	
		public static void clear(BaseWebElement element) {
			newInstance().clear(element);
		}
		
		public static void click(BaseWebElement element) {
			newInstance().click(element);
		}
		
		public static void sendKeys(BaseWebElement element, CharSequence... keysToSend){
			newInstance().sendKeys(element, keysToSend);
		}
		
		public static void submit(BaseWebElement element){
			newInstance().submit(element);
		}
		
		public static String getTagName(BaseWebElement element){
			return newInstance().getTagName(element);
		}	
		
		public static String getAttribute(BaseWebElement element, String attributeName){
			return newInstance().getAttribute(element, attributeName);
		}
		
		public static boolean isSelected(BaseWebElement element){
			return newInstance().isSelected(element);
		}
		
		public static boolean isEnabled(BaseWebElement element){
			return newInstance().isEnabled(element);
		}
		public static String getText(BaseWebElement element){
			return newInstance().getText(element);
		}
		public static boolean isDisplayed(BaseWebElement element){
			return newInstance().isDisplayed(element);
		}
		public static Point getLocation(BaseWebElement element){
			return newInstance().getLocation(element);
		}
		public static Dimension getSize(BaseWebElement element){
			return newInstance().getSize(element);
		}
		public static Rectangle getRect(BaseWebElement element){
			return newInstance().getRect(element);
		}
		public static String getCssValue(BaseWebElement element,String propertyName){			
			try {
				return newInstance().getCssValue(element,propertyName);
			}catch(Exception e) {
				throw new WebActionsExceptions(e.getMessage());
			}
		}
// End of Selenium WebElement Define Actions ---------------------------------------------------------------

	

// SeleniumCaller Define WebElementActions ------------------------------------------------------------------

	public static void type(BaseWebElement element, CharSequence... keysToSend){	
		newInstance().type(element, keysToSend);
	}
	
	public static void nativeClick(BaseWebElement element) {
		newInstance().nativeClick(element);
	}
	
	public static void check(BaseWebElement element) {
		newInstance().check(element);
	}
	
	public static void unCheck(BaseWebElement element) {
		newInstance().unCheck(element);
	}
	
	public static ComboBox toCombobox(BaseWebElement element) {
		return newInstance().toComboBox(element);
	}
	
	public static FrameElement toFrameElement(BaseWebElement element) {
		return newInstance().toFrameElement(element);
	}	
	
	// TODO : Need to implement
	public static EnhancedWebElement findElement(By locator){		
		return newInstance().findEnhancedWebElement(locator);
	}

	
	public static ExpectedConditions waitUntil(BaseWebElement element, long timeOutInSeconds) {
		return newInstance().waitUntil(element, timeOutInSeconds);
	}
	
	public static void dragAndDropTo(BaseWebElement fromElement, BaseWebElement toElement) {
		newInstance().dragAndDropTo(fromElement,toElement);
	}
	
	public static int getElementCount(BaseWebElement element) {
		return newInstance().getElementCount(element);
	}
// End Of SeleniumCaller Define Elements -------------------------------------------------------------------


	
	// Web Driver Navigation -------------------------------------------------------------------

	public static BrowserNavigation browserNavigate() {
		return newInstance().browserNavigate();
	}

	public static TargetLocatorFrame switchTo() {
		return newInstance().switchTo();
	}
	
	/** 
	 * @param windowHandle.
	 */
	public static void switchToWindowOrTab(String windowHandle) {
		newInstance().switchToWindowOrTab(windowHandle);
	}
	
	// SeleniumCaller Define Actions --------------------------------------------------------------------------
	
	public static <T> void waitUntil(ExpectedCondition<T> condition,long timeOutInSeconds){
			newInstance().waitUntil(condition,timeOutInSeconds);
	}

		
	public static void handleBrowserWindow(BrowserWindow browserWindow,long timeOutInSeconds) {
		newInstance().handleBrowserWindow(browserWindow,timeOutInSeconds);
			
	}
		
	public static DriverTimeOuts manageTimeouts() {
		return newInstance().manageTimeouts();
	}
		
	public static org.openqa.selenium.Alert alert() {
		return newInstance().alert();
	}

	// End of SeleniumCaller Define Actions -------------------------------------------------------------------

}
