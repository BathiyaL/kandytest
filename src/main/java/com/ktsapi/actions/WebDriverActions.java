package com.ktsapi.actions;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	String GoTo(String url);
	
	public String GetNewWindow(String url);
	
	public String GetNewTab(String url);
	
	@OverrideAction(name="getCurrentUrl()")
	public String GetUrl();
	
	@OverrideAction(name="getTitle()")
	public String GetTitle();

	
	@OverrideAction(name="getPageSource()")
	String GetPageSource();
	
	@OverrideAction(name="close()")
	void CloseBrowserWindow();	
	
	@OverrideAction(name="quit()")
	void CloseBrowser();
	
	WebDriver driver();

	@OverrideAction(name="getWindowHandle")
	String CurrentWindowHandle();
	
	@OverrideAction(name="getWindowHandles")
	Set<String> AllWindowHandles();
	
	String ParentWindowHandle();

//	public Navigation navigate();
	BrowserNavigation BrowserNavigate();
	TargetLocatorFrame SwitchTo();
	void SwitchToWindowOrTab(String windowHandle);
//	
//	public Options manage();
	
	void OpenBrowser();
	
	void HandleBrowserWindow(BrowserWindow browserWindow,long timeOutInSeconds);
	DriverTimeOuts ManageTimeouts();
	Alert Alert();
	
	 
}
