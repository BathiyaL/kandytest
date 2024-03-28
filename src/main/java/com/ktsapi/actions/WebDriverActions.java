package com.ktsapi.actions;

import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.annotation.ActionImplements;
import com.ktsapi.annotation.OverrideAction;
import com.ktsapi.elements.BrowserNavigation;
import com.ktsapi.elements.BrowserWindow;
import com.ktsapi.elements.TargetLocatorFrame;

////org.openqa.selenium.WebElement.WebDriver
// this will include selenium WebDriver methods here we can define what are the webdriver actions we going
// to exposure through seleniumcaller framework

@ActionImplements(name=WebDriver.class)
public interface WebDriverActions{


	@OverrideAction(name="get(String url)")
	String goTo(String url);
	
	public String getNewWindow(String url);
	
	public String getNewTab(String url);
	
	@OverrideAction(name="getCurrentUrl()")
	public String getUrl();
	
	@OverrideAction(name="getTitle()")
	public String getTitle();

	
	@OverrideAction(name="getPageSource()")
	String getPageSource();
	
	@OverrideAction(name="close()")
	void closeBrowserWindow();	
	
	@OverrideAction(name="quit()")
	void closeBrowser();
	
	WebDriver WebDriver();

	@OverrideAction(name="getWindowHandle")
	String currentWindowHandle();
	
	@OverrideAction(name="getWindowHandles")
	Set<String> allWindowHandles();
	
	String parentWindowHandle();

	BrowserNavigation browserNavigate();
	TargetLocatorFrame switchTo();
	void switchToWindowOrTab(String windowHandle);
	
	void openBrowser();
	
	void handleBrowserWindow(BrowserWindow browserWindow,long timeOutInSeconds);
	DriverTimeOuts manageTimeouts();
	Alert alert();
	
	 
}
