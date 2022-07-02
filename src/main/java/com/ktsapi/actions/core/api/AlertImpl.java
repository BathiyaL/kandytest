package com.ktsapi.actions.core.api;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.enums.ABotActions;

public class AlertImpl implements Alert{

	WebDriver webDriver;
	public AlertImpl(WebDriver activeWebDriver) {
		webDriver=activeWebDriver;
	}
	
	@Override
	public void dismiss() {
		try {
			webDriver.switchTo().alert().dismiss();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Alert_dismiss,"",null));
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.Alert_dismiss,ABotActions.Alert_dismiss.toString(), e));
			throw e;
		}	
	}

	@Override
	public void accept() {		
		try {
			webDriver.switchTo().alert().accept();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Alert_accept,"",null));
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.Alert_accept,ABotActions.Alert_accept.toString(), e));
			throw e;
		}	
	}

	@Override
	public String getText() {
		try {
			String alertText = webDriver.switchTo().alert().getText();
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Alert_getText,"",null));
			return alertText;
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.Alert_getText,ABotActions.Alert_getText.toString(), e));
			throw e;
		}		
	}

	@Override
	public void sendKeys(String keysToSend) {		
		try {
			webDriver.switchTo().alert().sendKeys(keysToSend);
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Alert_sendKeys,"",null));			
		} catch (Exception e){
			logAction(ActionLog.actionLogWithDirectMesageOnly(ABotActions.Alert_sendKeys,ABotActions.Alert_sendKeys.toString(), e));
			throw e;
		}		
	}

}
