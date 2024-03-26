package com.ktsapi.actions.core;

import static com.ktsapi.WebActons.driver;
import static com.ktsapi.actions.core.ActionsLogger.*;
import java.io.File;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.ktsapi.actions.CommonDriverAction;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.contexts.TestConfigurationDefaults;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.enums.ABotActions;
import com.ktsapi.utils.AvtomatUtils;
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
		systemLogsInfo(logMessage);
	}

	@Override
	public String baseUrl() {
		
		String baseUrlInCache = TestInitializr.getTestConfiguration().getBaseUrl();
		if(baseUrlInCache.equals(TestConfigurationDefaults.DEFAULT_BASE_URL)){
			systemLogsInfo("Base URL is same as system default url, it may have not overrode");
		}	
		return baseUrlInCache;
	}

	@Override
	public void saveScreenshot(String name) {
		try {
			Path path = AvtomatUtils.getRunningTestNGTestInstanceFolder().resolve(name+".png");
			if(driver() != null) {
				TakesScreenshot scrShot =((TakesScreenshot)driver());
				File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
				File destFile=new File(path.toString());
				FileUtils.copyFile(srcFile, destFile);
			}
		} catch (Exception e) {
			ConfigLogger.logError("Error occured while saving screensho");
		}
	}
}
