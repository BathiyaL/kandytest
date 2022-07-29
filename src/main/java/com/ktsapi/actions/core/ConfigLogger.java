package com.ktsapi.actions.core;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLogger {
	//private static final Logger LOG = Logger.getLogger("CONFIG"); 
	private static final Logger LOG = LogManager.getLogger("CONFIG");
	
	public static void logInfo(String loggerMessage) {
		LOG.info(loggerMessage);
	}

	public static void logWarn(String loggerMessage) {
		LOG.warn(loggerMessage);
	}
	
	public static void logError(String loggerMessage) {
		LOG.error(loggerMessage);
	}
}
