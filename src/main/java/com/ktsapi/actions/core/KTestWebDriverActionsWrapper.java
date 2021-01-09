package com.ktsapi.actions.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.ktsapi.actions.KTestWebDriverActions;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestConfig;
import com.ktsapi.enums.KTestActions;

import static com.ktsapi.actions.core.ActionsLogger.logAction;

public class KTestWebDriverActionsWrapper implements KTestWebDriverActions {


	public WebDriver driver(){		
		return TestConfig.getDriver();
	}
	
	public void OpenBrowser() {
		//driver = TestCache.getWebDriver();
		driver();
		String launchedBrowser = "Chrome";
		logAction(ActionLog.actionLogWithDirectMesage(KTestActions.OpenBrowser, launchedBrowser, null));		
	}
	
	public String GoTo(String url) {
		String logMessage = "GoTo "+url;
		String logMessage2 = " " + url;
		String windowHandle = null;
		try {
			driver().get(url);
			windowHandle =  driver().getWindowHandle();
			logAction(ActionLog.actionLogWithDirectMesageOnly(KTestActions.GoTo,logMessage,null));	
		}catch(Exception e) {			
			logAction(ActionLog.actionLogWithDirectMesage(KTestActions.GoTo,logMessage2,e));
			throw new RuntimeException(e); 
		}	
		return windowHandle;
	}

	public <C> C GetWebPage(Class<C> page) {
		// TODO Auto-generated method stub
		return null;
	}

	public void Log(String loggerMessage) {
		// TODO Auto-generated method stub
		
	}

}
