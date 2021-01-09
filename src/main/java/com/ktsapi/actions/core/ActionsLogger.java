package com.ktsapi.actions.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestConfig;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.enums.KTestActions;

public class ActionsLogger {

	private static final Logger LOG = Logger.getLogger(ActionsLogger.class);
	
	public static void logAction(ActionLog actionObject) {

		/*
		 * Script is not going to fail due to error in the action logs
		 */
		try {
			TestConfig.getTestActionsList().add(actionObject);
			
			if(actionObject.getStackTraceString()!=null && !actionObject.getStackTraceString().isEmpty() && !actionObject.getStackTraceString().equals("N/A")) {
				String errorString = "\n#######################<FAILURE MESSAGE>########################### \n" + actionObject.getStackTraceString() + "\n#######################</FAILURE MESSAGE>########################## \n";			
				TestConfig.getTestLogger().error(actionObject.getActionLogString() + "\n" + errorString);
			}else {
				String returnValue = "";
				if(actionObject.getReturnValue()!=null  && !actionObject.getReturnValue().equals("N/A")) {
					returnValue = " @return="+ actionObject.getReturnValue();
				}			
				TestConfig.getTestLogger().info(actionObject.getActionLogString() + returnValue);
			}
		}catch(Exception e) {			
			LOG.error("Error occuer while append action logs -> " + e);
		}


	}

//	protected static String getLocator(String element) {
//		String locator = null;
//		if (element != null) {
//			String elmStr = element.trim();			
//			if (elmStr.contains("->")) {
//				locator = elmStr.toString().substring(elmStr.indexOf("->") + 2, elmStr.lastIndexOf("]")).trim();
//			} else {
//				TestConfig.getTestLogger().warn("Element not found !");
//			}
//
//		}
//
//		return locator;
//	}
//	
//	protected static String getElementLocatorMsg(String element, String taName){
//		String s = null;
//		s = "@elment{"+ taName.trim() + "(" + getLocator(element) + ")}" ;
//		
//		return s;
//	}
//	
//	protected static void getActionsLoggerMsg(KTestActions action, String element,String tagName, String value){		
//		try {
//			TestConfig.getTestLogger().info("[Actions] "+action+"[" + getElementLocatorMsg(element,tagName) +"] @value=" + value); // changed -> to @value
//		} catch(Exception e){
//			System.out.println("[Actions] "+action+"Unbale to Fetch loger Message");
//		}	
//	}
//	
//	protected static void getActionsLogger(KTestActions action, String fieldName, String value){
//		TestConfig.getTestLogger().info("[Actions] "+action+"[@elment(" + fieldName + ")] @value=" + value);	 // changed -> to @value
//	}
//	protected static void getActionsLogger(KTestActions action, String fieldName){
//		TestConfig.getTestLogger().info("[Actions] "+action+"[@elment(" + fieldName + ")]");	
//	}
//
//	protected static void getActionsLoggerMsg(KTestActions action, String element,String tagName){
//		try {
//			TestConfig.getTestLogger().info("[Actions] "+action+"[" + getElementLocatorMsg(element,tagName) +"]");
//		} catch(Exception e){
//			System.out.println("[Actions] "+action+"Unbale to Fetch loger Message");
//		}		
//	}
//	
//	protected static void userLogs(String loggerMessage){		
//		try {
//			TestConfig.getTestLogger().info("[Actions] Log -> " + loggerMessage );
//		} catch(Exception e){
//			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
//		}	
//	}
//	
//	protected static void systemLogsInfo(String loggerMessage){		
//		try {
//			TestConfig.getTestLogger().info(loggerMessage);
//		} catch(Exception e){
//			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
//		}	
//	}
//	
//	protected static void systemLogsWarn(String loggerMessage){		
//		try {
//			TestConfig.getTestLogger().warn(loggerMessage);
//		} catch(Exception e){
//			System.out.println("[Actions] Log -> Unbale to Fetch loger Message");
//		}	
//	}
//
//	protected static void logAction(BaseWebElement element, KTestActions action, String value) {
//		if (element.getFieldName() != null) {
//			getActionsLogger(action, element.getFieldName(), value);
//		} else if (element.getByLocator() != null) {
//			getActionsLogger(action, element.getByLocator().toString(), value);
//		} else if (element.asWebelement() != null) {
//			String elmentStr = element.asWebelement().toString();
//			String tagName = element.asWebelement().getTagName();
//			getActionsLoggerMsg(action, elmentStr, tagName, value);
//		}
//	}
//
//	protected static void logAction(String element, KTestActions action, String value) {
//		try {
//			TestConfig.getTestLogger().info("[Actions] "+action+"[" + element +"] @value=" + value);// changed -> to @value
//		} catch(Exception e){
//			System.out.println("[ActionsLOGError] "+action+"Unbale to Fetch loger Message");
//		}
//	}
//	protected static void logAction(BaseWebElement element, KTestActions action) {
//		if (element.getFieldName() != null) {
//			getActionsLogger(action, element.getFieldName());
//		} else if (element.getByLocator() != null) {
//			getActionsLogger(action, element.getByLocator().toString());
//		} else if (element.asWebelement() != null) {
//			String elmentStr = element.asWebelement().toString();
//			String tagName = element.asWebelement().getTagName();
//			getActionsLoggerMsg(action, elmentStr, tagName);
//		}
//	}
//	
//	protected static void logActionError(String element, KTestActions action, String value, String errorMessage) {
//		try {
//			TestConfig.getTestLogger().error("[ActionsError] {"+action+"[" + element +"] @value=" + value + " } : Error Message . . . . ." + "\n " +errorMessage + "\n "); // changed -> to @value
//		} catch(Exception e){
//			System.out.println("[ActionsLOGError] "+action+"Unbale to Fetch loger Message");
//		}
//	}
//	
//	protected static void getActionErrorLogger(String eMsg, KTestActions action) {
//		String[] lines = eMsg.split("\n");
//		TestConfig.getTestLogger().error("[Actions] "+action+"[]-> FAIL :: " + lines[0]);
//	}
//
//	public static String getElementLog(BaseWebElement element, String errorMessage){
//		String elementLogStr = "";
//		if (element.getFieldName() != null) {
//			elementLogStr = element.getFieldName();
//		} else if (element.getByLocator() != null) {
//			elementLogStr = element.getByLocator().toString();
//		} else if (element.asWebelement() != null) {
//			if (errorMessage == null) {
//				String elmentStr = element.asWebelement().toString();
//				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + elmentStr);
//				String tagName = element.asWebelement().getTagName();
//				elementLogStr = "{" + tagName + "(" + getLocator(elmentStr) + ")}";
//			}else{
//				elementLogStr = element.asWebelement().toString();
//			}
//
//		}
//		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + elementLogStr);
//		return elementLogStr;
//	}	
//	
//	protected static String getStackTrace(Throwable t) {
//		StringWriter sw = new StringWriter();
//		t.printStackTrace(new PrintWriter(sw));
//		return sw.toString();
//	}
	
}
