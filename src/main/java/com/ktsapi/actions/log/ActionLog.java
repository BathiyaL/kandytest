package com.ktsapi.actions.log;

import com.ktsapi.enums.ABotActions;

public class ActionLog {
	private ABotActions action;
	private String element;
	private String value;
	private String errorMssage;
	private String stackTraceString;
	private String actionLogString;
	private String returnValue;
	private String warning;	
	
	private transient Throwable stackTrace; // By default, Gson would exclude a field from serialization and deserialization – both, if we simply mark the field as transient
	
	public ActionLog(ABotActions action,String element,String actionParamValue, String logMessage, Throwable stackTrace) {
		this.action = action;
		this.element = element;
		String logElement = "NA";
		if(this.element!=null) {
			String[] names = this.element.replaceFirst("class ", "").split("\\.");
			logElement = names[names.length-2].concat(".").concat(names[names.length-1]);
		}
		if(actionParamValue!=null){
			value = actionParamValue;			
			//actionLogString = action + "[" + logElement + "]" + " @value=" + this.value; // changed -> to @value
		}else {
			value = "N/A";
			//actionLogString = action + "[" + logElement + "]" ;
		}
		
		if(logMessage!=null) {
			if(logMessage.contains("%s")) {
				actionLogString = String.format(logMessage, logElement);
			}else {
				actionLogString = action + "[" + logElement + "]" + " @value=" + this.value; // TODO : This need to remove after refactor all
			}
		}else {
			value = "N/A";
			actionLogString = action + "[" + logElement + "]" ;
		}
		
		if(stackTrace!=null){
			this.stackTrace = stackTrace;
			this.errorMssage = stackTrace.getMessage();
			this.stackTraceString = stackTrace.toString();//TODO: get string
			actionLogString = actionLogString + " :: FAIL";
		}else{
			this.errorMssage = "N/A";
			this.stackTraceString = "N/A";
		}
	}
	public ActionLog(ABotActions action,boolean isLogMessageOnly ,String logMessage, Throwable stackTrace) {
		this.action = action;
		this.element = "NA";
		this.value = "NA";
		if(isLogMessageOnly) {
			this.actionLogString = action + " " + logMessage;
		}else {
			this.actionLogString = logMessage;
		}
		if(stackTrace!=null){
			this.stackTrace = stackTrace;
			this.errorMssage = stackTrace.getMessage();
			this.stackTraceString = stackTrace.toString();//TODO: get string
			actionLogString = actionLogString + " :: FAIL";
		}else{
			this.errorMssage = "N/A";
			this.stackTraceString = "N/A";
		}
		
		
	}
	
	public static ActionLog ActionLogWithoutReturnValue(ABotActions action,String element,String actionParamValue, Throwable stackTrace, String logMessage) {
		ActionLog actionLog = new ActionLog(action,element,actionParamValue,logMessage, stackTrace);
		actionLog.returnValue = "N/A";
		return actionLog;
	}
	
	public static ActionLog ActionLogWithWarningsAndWithoutReturnValue(ABotActions action,String element,String actionParamValue, Throwable stackTrace,String Warning) {
		ActionLog actionLog = new ActionLog(action,element,actionParamValue,null, stackTrace);
		actionLog.returnValue = "N/A";
		actionLog.warning = Warning;
		return actionLog;
	}
	
	public static ActionLog actionLogWithDirectMesage(ABotActions action, String logMessage,Throwable stackTrace) {
		ActionLog actionLog = new ActionLog(action,true,logMessage, stackTrace);
		return  actionLog;
	}
	public static ActionLog actionLogWithDirectMesage(ABotActions action, String logMessage,Throwable stackTrace,String returnValue) {
		ActionLog actionLog = new ActionLog(action,true,logMessage, stackTrace);
		actionLog.returnValue = returnValue;
		return  actionLog;
	}
	public static ActionLog actionLogWithDirectMesageOnly(ABotActions action, String logMessage,Throwable stackTrace) {
		ActionLog actionLog = new ActionLog(action,false,logMessage, stackTrace);
		return  actionLog;
	}
	public static ActionLog actionLogWithDirectMesageOnly(ABotActions action, String logMessage,Throwable stackTrace,String returnValue) {
		ActionLog actionLog = new ActionLog(action,false,logMessage, stackTrace);
		actionLog.returnValue = returnValue;
		return  actionLog;
	}
	
	public static ActionLog ActionLogWithReturnValue(ABotActions action,String element,String actionParamValue, Throwable stackTrace, String returnValue,String logMessage) {
		ActionLog actionLog = new ActionLog(action,element,actionParamValue,logMessage, stackTrace);
		actionLog.returnValue = returnValue;
		return actionLog;
	}
	
	public ABotActions getAction() {
		return action;
	}
	public void setAction(ABotActions action) {
		this.action = action;
	}
	
	public String getErrorMssage() {
		return errorMssage;
	}
	public void setErrorMssage(String errorMessage) {
		this.errorMssage = errorMessage;
	}
	
	public Throwable getStackTrace() {
		return stackTrace;
	}
	public void setgetStackTrace(Throwable error) {
		this.stackTrace = error;
	}

	public String getStackTraceString() {
		return stackTraceString;
	}

	public void setStackTraceString(String stackTraceString) {
		this.stackTraceString = stackTraceString;
	}
	
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	
	public String getTextValue() {
		return value;
	}
	public void setTextValue(String textValue) {
		this.value = textValue;
	}

	
	public String getActionLogString(){
		return actionLogString;
	}

	public void setActionLogString(String actionLogString) {
		this.actionLogString = actionLogString;
	}
	
	public String getReturnValue(){
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	public String getWarning(){
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	
}
