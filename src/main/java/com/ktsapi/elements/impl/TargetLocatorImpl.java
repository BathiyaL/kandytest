package com.ktsapi.elements.impl;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ktsapi.actions.core.api.AlertImpl;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.TargetLocatorFrame;
import com.ktsapi.enums.ABotActions;


public class TargetLocatorImpl implements TargetLocatorFrame{

	WebDriver webDriver;
	public TargetLocatorImpl(WebDriver activeWebDriver){
		webDriver = activeWebDriver;
	}
	
	@Override
	public void frame(int index) {
		webDriver.switchTo().frame(index);
	}

	@Override
	public void frame(String nameOrId) {
		try {
			webDriver.switchTo().frame(nameOrId);
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_frame,"Switch to frame by name or ID, "+nameOrId,null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_frame, "Switch to frame by name or ID, "+nameOrId, e));
			throw e;
		}		
	}

	@Override
	public void frame(BaseWebElement frameElement) {		
		try {
			webDriver.switchTo().frame(frameElement.asWebelement());
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_frame,"Switch to frame by @WebElement",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_frame, "Switch to frame by @WebElement", e));
			throw e;
		}	
	}

	@Override
	public void parentFrame() {		
		try {
			webDriver.switchTo().parentFrame();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_parentFrame,"Switch to parent frame ",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_parentFrame, "Switch to parent frame", e));
			throw e;
		}
	}

	@Override
	public void window(String nameOrHandle) {		
		try {
			webDriver.switchTo().window(nameOrHandle);
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_window,"Switch to window "+nameOrHandle,null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_window, "Switch to window "+nameOrHandle, e));
			throw e;
		}
	}

	@Override
	public void defaultContent() {		
		try {
			webDriver.switchTo().defaultContent();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_defaultContent,"Switch to default content ",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.SwitchTo_defaultContent, "Switch to default content ", e));
			throw e;
		}
	}

	@Override
	public WebElement activeElement() {
		return webDriver.switchTo().activeElement();
	}

	@Override
	public Alert alert() {
		return new AlertImpl(webDriver);
	}

}
