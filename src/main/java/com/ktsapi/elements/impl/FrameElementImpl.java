package com.ktsapi.elements.impl;

import static com.ktsapi.WebActons.SwitchTo;
import static com.ktsapi.actions.core.ActionsLogger.getElementLog;
import static com.ktsapi.actions.core.ActionsLogger.logAction;

import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.core.ActionsLogger;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.FrameElement;
import com.ktsapi.enums.ABotActions;

public class FrameElementImpl extends BaseWebElementImpl implements FrameElement{

	WebDriver webDriver;
	protected BaseWebElement parentBaseWebElement = null;
	
	/**
	 * 
	 * @param baseWebElement
	 * @param activeWebDriver
	 * 
	 * without passing webdriver we can directly use SwitchTo() action, but in that cases we having havae to take logs form SwitchTo() also
	 * this will confuse the user logs with actions, hence passed the driver and handle the swithcing
	 */
	public FrameElementImpl(BaseWebElement baseWebElement,WebDriver activeWebDriver) {
		super(baseWebElement.asWebelement(), baseWebElement.getByLocator(), baseWebElement.getFieldName());
		parentBaseWebElement = baseWebElement;
		webDriver = activeWebDriver;
	}

	@Override
	public void switchToFrame() {
		try {			
			webDriver.switchTo().frame(parentBaseWebElement.asWebelement());
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToFrameElement_switchToFrame,getElementLog(parentBaseWebElement,null),null,null,null));
			//ActionsLogger.logAction(ActionLog.ActionLogWithReturnValue(ABotActions.ToFrameElement_switchToFrame,getElementLog(parentBaseWebElement,null),null,null,"true"));
		} catch (Exception e){
			ActionsLogger.logAction(ActionLog.ActionLogWithoutReturnValue(ABotActions.ToFrameElement_switchToFrame,getElementLog(parentBaseWebElement,e.getMessage()),null,e,null));
			throw e;
		}
		
	}
	
	@Override
	public void switchToParentFrame() {		
		try {
			webDriver.switchTo().parentFrame();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.ToFrameElement_parentFrame,"Switch to parent frame ",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.ToFrameElement_parentFrame, "Switch to parent frame", e));
			throw e;
		}
	}
	
	@Override
	public void switchToDefaultContent() {		
		try {
			webDriver.switchTo().defaultContent();
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.ToFrameElement_defaultContent,"Switch to default content ",null));	
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.ToFrameElement_defaultContent, "Switch to default content ", e));
			throw e;
		}
	}

}
