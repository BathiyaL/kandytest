package com.ktsapi.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
public class AvtomatUtils {
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String getWindowsLoggedInUser() {
		return System.getProperty("user.name");
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static LocalDateTime getCurretnTimeStamp() {
		return LocalDateTime.now();
	}
	
	public static String localDateTimeStringFormat(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(localDateTime);
	}

}
