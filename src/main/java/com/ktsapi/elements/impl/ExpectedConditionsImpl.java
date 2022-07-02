package com.ktsapi.elements.impl;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import java.time.Duration;
import java.time.LocalDateTime;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.elements.ExpectedConditions;
import com.ktsapi.enums.ABotActions;

public class ExpectedConditionsImpl extends BaseWebElementImpl implements ExpectedConditions{

	protected Select selectElement = null;
	protected BaseWebElement parentBaseWebElement = null;
	protected long waitTime=0;
	WebDriverWait webDriverWait;
	
	public ExpectedConditionsImpl(BaseWebElement baseWebElement,long timeOutInSeconds) {
		super(baseWebElement.asWebelement(), baseWebElement.getByLocator(), baseWebElement.getFieldName());
		parentBaseWebElement = baseWebElement;
		waitTime = timeOutInSeconds;
		KandyTestWebDriverActionsImpl actionImpl = new KandyTestWebDriverActionsImpl();
		//selectElement = new Select(WebActons.$$(baseWebElement.getByLocator()));		
		//selectElement = new Select(actionImpl.$$(baseWebElement));
		
		webDriverWait = new WebDriverWait(actionImpl.driver(), Duration.ofSeconds(waitTime));		
		actionImpl.switchToFrames(baseWebElement);
	}

	@Override
	public void invisibilityOfElementLocated() {
		String msg = "WaitUntil("+ waitTime + "s) -> invisibilityOfElementLocated by " + parentBaseWebElement.getFieldName();
		try {
			//System.out.println("=================================================================parentBaseWebElement.getByLocator() :: " + LocalDateTime.now());
			webDriverWait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(parentBaseWebElement.getByLocator()));
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,null));
			//System.out.println("=================================================================" + LocalDateTime.now());
		}catch(Exception ex) {
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.WaitUntil,msg,ex));	
			throw ex;
		}
		
	}

}
