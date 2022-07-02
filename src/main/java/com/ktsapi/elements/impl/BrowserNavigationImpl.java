package com.ktsapi.elements.impl;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BrowserNavigation;
import com.ktsapi.enums.ABotActions;

public class BrowserNavigationImpl implements BrowserNavigation{

	WebDriver webDriver;
	public BrowserNavigationImpl(WebDriver activeWebDriver){
		webDriver = activeWebDriver;
	}
	@Override
	public void back() {
		try {
			webDriver.navigate().back();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_back,"Browser navigate back",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_back, "Browser navigate back", e));
			throw e;
		}		
	}

	@Override
	public void forward() {
		try {
			webDriver.navigate().forward();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_forward,"Browser navigate forward",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_forward, "Browser navigate forward", e));
			throw e;
		}		
	}

	@Override
	public void to(String url) {
		try {
			webDriver.navigate().to(url);
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_to,"Browser navigate to "+url,null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_to, "Browser navigate to", e));
			throw e;
		}	
		
	}

	@Override
	public void to(URL url) {
		try {
			webDriver.navigate().to(url);
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_to,"Browser navigate to "+url.toString(),null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_to, "Browser navigate to", e));
			throw e;
		}		
	}

	@Override
	public void refresh() {
		try {
			webDriver.navigate().refresh();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_refresh,"Browser refresh ",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.BrowserNavigate_refresh, "Browser refresh", e));
			throw e;
		}		
		
	}

}
