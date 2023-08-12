package com.ktsapi.actions.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.ktsapi.actions.log.ActionLog;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.elements.BaseWebElement;
import com.ktsapi.enums.ABotActions;



public class ActionsLogger {

	public static void logAction(ActionLog actionObject) {
		TestInitializr.getTestActionsList().add(actionObject);
		
		if(actionObject.getStackTraceString()!=null && !actionObject.getStackTraceString().isEmpty() && !actionObject.getStackTraceString().equals("N/A")) {
			String errorString = "\n#######################<FAILURE MESSAGE>########################### \n" + actionObject.getStackTraceString() + "\n#######################</FAILURE MESSAGE>########################## \n";			
			TestInitializr.getTestLogger().error(actionObject.getActionLogString() + "\n" + errorString);
		}
//		else {
//			String returnValue = "";
//			if(actionObject.getReturnValue()!=null  && !actionObject.getReturnValue().equals("N/A")) {
//				returnValue = " @return="+ actionObject.getReturnValue();
//			}			
//			TestInitializr.getTestLogger().info(actionObject.getActionLogString() + returnValue);
//		}
		// DOC BL : decide not to print log for every action atm, since it can also found with the json log
		
	}

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

	protected static void getActionErrorLogger(String eMsg, ABotActions action) {
		String[] lines = eMsg.split("\n");
		TestInitializr.getTestLogger().error("[Actions] "+action+"[]-> FAIL :: " + lines[0]);
	}

	public static String getElementLog(BaseWebElement element, String errorMessage){
		String elementLogStr = "";
		if (element.getFieldName() != null) {
			elementLogStr = element.getFieldName();
		} else if (element.getByLocator() != null) {
			elementLogStr = element.getByLocator().toString();
		} else if (element.asWebelement() != null) {
			if (errorMessage == null) {
				String elmentStr = element.asWebelement().toString();
				String tagName = element.asWebelement().getTagName();
				elementLogStr = "{" + tagName + "(" + getLocator(elmentStr) + ")}";
			}else{
				elementLogStr = element.asWebelement().toString();
			}

		}
		return elementLogStr;
	}	
	
	protected static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
}
