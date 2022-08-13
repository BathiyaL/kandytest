package com.ktsapi.actions.core;

import static com.ktsapi.actions.core.ActionsLogger.*;

import com.ktsapi.actions.CommonDriverAction;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.contexts.TestConfigurationDefaults;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.ABotActions;
public class CommonDriverActionImpl implements CommonDriverAction {

	@Override
	public void pause(int timeOutInSeconds){
		String logMessage = "for " + timeOutInSeconds + " seconds";
    	try {    		
			Thread.sleep(timeOutInSeconds*1000l);// convert seconds to millis
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Pause,logMessage,null));	
		} catch (InterruptedException e) {
			logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Pause,logMessage,e));
			Thread.currentThread().interrupt(); // if this cause unexpected result remove 
		}
	}

	@Override
	public void print(String logMessage) {
		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.Print,logMessage,null));		
	}

	@Override
	public String baseUrl() {
		
		String baseUrlInCache = TestInitializr.getTestConfiguration().getBaseUrl();
		if(baseUrlInCache.equals(TestConfigurationDefaults.DEFAULT_BASE_URL)){
			systemLogsInfo("Base URL is same as system default url, it may have not overrode");
		}
		String logMessage = "Set as : " + baseUrlInCache;
//		logAction(ActionLog.actionLogWithDirectMesage(ABotActions.BaseUrl,logMessage,null));	
		return baseUrlInCache;
	}

}
