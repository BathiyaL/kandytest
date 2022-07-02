package com.ktsapi.actions.core.api;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.ABotActions;

public class DriverTimeOutsImpl implements DriverTimeOuts {

	WebDriver webDriver;
	public DriverTimeOutsImpl(WebDriver activeWebDriver) {
		webDriver=activeWebDriver;
	}
	
	@Override
	public void setImplicitlyWaitTime(long implicitlyWaitTimeInSeconds) {
		TestInitializr.setImplicitlyWaitTime(implicitlyWaitTimeInSeconds);
		webDriver.manage().timeouts().implicitlyWait(implicitlyWaitTimeInSeconds, TimeUnit.SECONDS);
		String msg = "Set ImplicitlyWaitTime to " + implicitlyWaitTimeInSeconds + " seconds";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.ManageTimeOuts_setImplicitlyWaitTime,msg,null));
	}

	@Override
	public void setScriptTimeout(long scriptTimeoutInSeconds) {
		TestInitializr.setScriptTimeout(scriptTimeoutInSeconds);
		webDriver.manage().timeouts().setScriptTimeout(scriptTimeoutInSeconds, TimeUnit.SECONDS);
		String msg = "Set ScriptTimeout to " + scriptTimeoutInSeconds + " seconds";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.ManageTimeOuts_setScriptTimeout,msg,null));
	}

	@Override
	public void setPageLoadTimeout(long pageLoadTimeoutInSeconds) {
		TestInitializr.setPageLoadTimeout(pageLoadTimeoutInSeconds);
		webDriver.manage().timeouts().pageLoadTimeout(pageLoadTimeoutInSeconds, TimeUnit.SECONDS);
		String msg = "Set PageLoadTimeout to " + pageLoadTimeoutInSeconds + " seconds";
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.ManageTimeOuts_setPageLoadTimeout,msg,null));
	}

}
