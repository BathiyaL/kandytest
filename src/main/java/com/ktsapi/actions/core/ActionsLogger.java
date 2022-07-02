package com.ktsapi.actions.core;

import static com.ktsapi.actions.core.ActionsLogger.getActionsLogger;
import static com.ktsapi.actions.core.ActionsLogger.getActionsLoggerMsg;
import static com.ktsapi.actions.core.ActionsLogger.getLocator;
import static com.ktsapi.actions.core.ActionsLogger.logAction;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.logging.Log;
import org.openqa.selenium.WebElement;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.enums.ABotActions;

//import ch.qos.logback.core.net.SyslogOutputStream;

public class ActionsLogger {
	//private static final Logger LOG = LoggerFactory.getLogger(""); //LoggerFactory.getLogger(AvtomatBotWebDriverActions.class); // need to get from TestCache
//	private static List<Object> actionsList;  // need to get from TestCache
	
//	private static List<Object> initActionList(){
//		if(actionsList == null){
//			actionsList = new ArrayList<Object>();
//		}
//		return actionsList;
//	}
//	public static void addToActionsList(Object actionObject){
//		//initActionList().add(actionObject);
//		TestCache.getTestActionsList().add(actionObject);
//	}
	public static void logAction(ActionLog actionObject) {
		//initActionList().add(actionObject);
		TestInitializr.getTestActionsList().add(actionObject);
		
		if(actionObject.getStackTraceString()!=null && !actionObject.getStackTraceString().isEmpty() && !actionObject.getStackTraceString().equals("N/A")) {
			String errorString = "\n#######################<FAILURE MESSAGE>########################### \n" + actionObject.getStackTraceString() + "\n#######################</FAILURE MESSAGE>########################## \n";			
			TestInitializr.getTestLogger().error(actionObject.getActionLogString() + "\n" + errorString);
		}else {
			String returnValue = "";
			if(actionObject.getReturnValue()!=null  && !actionObject.getReturnValue().equals("N/A")) {
				returnValue = " @return="+ actionObject.getReturnValue();
			}			
			TestInitializr.getTestLogger().info(actionObject.getActionLogString() + returnValue);
		}
		
			
//			if(actionObject.getErrorMssage().equals("N/A")){
//				logAction(actionObject.getElement(),actionObject.getAction(),obj.getValue());
//			}else {
//				//logActionError(obj.getElement(),obj.getAction(),obj.getValue(),obj.getErrorMssage());
//			}
			

		
	}
//	public static List<Object> getActionsList(){
//		return initActionList();
//	}

	protected static String getLocator(String element) {
		String locator = null;
		if (element != null) {
			String elmStr = element.trim();			
			if (elmStr.contains("->")) {
				locator = elmStr.toString().substring(elmStr.indexOf("->") + 2, elmStr.lastIndexOf("]")).trim();
			} else {
				TestInitializr.getTestLogger().warn("Element not found !");
			}

		}

		return locator;
	}
	
	protected static String getElementLocatorMsg(String element, String taName){
		String s = null;
		s = "@elment{"+ taName.trim() + "(" + getLocator(element) + ")}" ;
		
		return s;
	}
	
	protected static void getActionsLoggerMsg(ABotActions action, String element,String tagName, String value){		
		try {
			TestInitializr.getTestLogger().info("[Actions] "+action+"[" + getElementLocatorMsg(element,tagName) +"] @value=" + value); // changed -> to @value
		} catch(Exception e){
			System.out.println("[Actions] "+action+"Unbale to Fetch loger Message");
		}	
	}
	
	protected static void getActionsLogger(ABotActions action, String fieldName, String value){
		TestInitializr.getTestLogger().info("[Actions] "+action+"[@elment(" + fieldName + ")] @value=" + value);	 // changed -> to @value
	}
	protected static void getActionsLogger(ABotActions action, String fieldName){
		TestInitializr.getTestLogger().info("[Actions] "+action+"[@elment(" + fieldName + ")]");	
	}

	protected static void getActionsLoggerMsg(ABotActions action, String element,String tagName){
		try {
			TestInitializr.getTestLogger().info("[Actions] "+action+"[" + getElementLocatorMsg(element,tagName) +"]");
		} catch(Exception e){
			System.out.println("[Actions] "+action+"Unbale to Fetch loger Message");
		}		
	}
	
//	protected static void getActionsLoggerMsg(String action, String value){		
//		try {
//			LOG.info("[Actions] "+action+ " -> " + value);
//		} catch(Exception e){
//			System.out.println("[Actions] "+action+" -> Unbale to Fetch loger Message");
//		}	
//	}
	
	protected static void userLogs(String loggerMessage){		
		try {
			TestInitializr.getTestLogger().info("[Actions] Log -> " + loggerMessage );
		} catch(Exception e){
			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
		}	
	}
	
	protected static void systemLogsInfo(String loggerMessage){		
		try {
			TestInitializr.getTestLogger().info(loggerMessage);
		} catch(Exception e){
			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
		}	
	}
	
	protected static void systemLogsWarn(String loggerMessage){		
		try {
			TestInitializr.getTestLogger().warn(loggerMessage);
		} catch(Exception e){
			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
		}	
	}

	protected static void logAction(BaseWebElement element, ABotActions action, String value) {
		if (element.getFieldName() != null) {
			getActionsLogger(action, element.getFieldName(), value);
		} else if (element.getByLocator() != null) {
			getActionsLogger(action, element.getByLocator().toString(), value);
		} else if (element.asWebelement() != null) {
			String elmentStr = element.asWebelement().toString();
			String tagName = element.asWebelement().getTagName();
			getActionsLoggerMsg(action, elmentStr, tagName, value);
		}
	}

	protected static void logAction(String element, ABotActions action, String value) {
		try {
			TestInitializr.getTestLogger().info("[Actions] "+action+"[" + element +"] @value=" + value);// changed -> to @value
		} catch(Exception e){
			System.out.println("[ActionsLOGError] "+action+"Unbale to Fetch loger Message");
		}
	}
	protected static void logAction(BaseWebElement element, ABotActions action) {
		if (element.getFieldName() != null) {
			getActionsLogger(action, element.getFieldName());
		} else if (element.getByLocator() != null) {
			getActionsLogger(action, element.getByLocator().toString());
		} else if (element.asWebelement() != null) {
			String elmentStr = element.asWebelement().toString();
			String tagName = element.asWebelement().getTagName();
			getActionsLoggerMsg(action, elmentStr, tagName);
		}
	}
	
	protected static void logActionError(String element, ABotActions action, String value, String errorMessage) {
		try {
			TestInitializr.getTestLogger().error("[ActionsError] {"+action+"[" + element +"] @value=" + value + " } : Error Message . . . . ." + "\n " +errorMessage + "\n "); // changed -> to @value
		} catch(Exception e){
			System.out.println("[ActionsLOGError] "+action+"Unbale to Fetch loger Message");
		}
	}
	
//	protected static void getNoSuchElementLogger(String eMsg, ABotActions action) {
//
//		String[] lines = eMsg.split("\n");
//		LOG.error("[Actions] "+action+"[]-> ERROR :: " + lines[0]);
//		
////		try {
////			if (eMsg != null) {
////				String locatorStr;
////				String[] locators;
////				String[] method = null;
////				String[] selector = null;
////				if (eMsg != null) {
////					if (eMsg.contains("Unable to locate element")) {
////						locatorStr = eMsg.substring(eMsg.indexOf("Unable to locate element"), eMsg.indexOf("}") + 1);
////						locators = locatorStr.replaceAll("[{}]", "").trim().split(",");
////						method = locators[0].split(":");
////						selector = locators[1].split(":");
////					}
////				}
////				LOG.error("[Actions] "+action+"[@elment{" + method[1].replace("\"", "").trim() + ": "
////						+ selector[1].replace("\"", "").trim() + "}] -> Unable to locate element");
////			} else {
////				LOG.error("[Actions] "+action+"[]-> Unable to locate element");
////			}
////
////		} catch (Exception e) {
////			LOG.error("[Actions] "+action+"[]-> Unable to locate element");
////		}
//	}
	
	protected static void getActionErrorLogger(String eMsg, ABotActions action) {
		String[] lines = eMsg.split("\n");
		TestInitializr.getTestLogger().error("[Actions] "+action+"[]-> FAIL :: " + lines[0]);
	}
	
//	public static void getElementWarningLogger(String eMsg, String element) {
//		//String[] lines = eMsg.split("\n");
//		LOG.warn("[Element] "+element+"[]-> Warning :: " + eMsg);
//	}
	
	public static String getElementLog(BaseWebElement element, String errorMessage){
		String elementLogStr = "";
		if (element.getFieldName() != null) {
			elementLogStr = element.getFieldName();
		} else if (element.getByLocator() != null) {
			elementLogStr = element.getByLocator().toString();
		} else if (element.asWebelement() != null) {
			if (errorMessage == null) {
				String elmentStr = element.asWebelement().toString();
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + elmentStr);
				String tagName = element.asWebelement().getTagName();
				elementLogStr = "{" + tagName + "(" + getLocator(elmentStr) + ")}";
			}else{
				elementLogStr = element.asWebelement().toString();
			}

		}
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + elementLogStr);
		return elementLogStr;
	}	
	
	protected static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
}
