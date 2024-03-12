package com.ktsapi.exceptions;
import org.testng.SkipException;

import com.ktsapi.core.TestInitializr;

public class OSNotSupportException extends SkipException {

	private static final long serialVersionUID = 1L;
	
	public OSNotSupportException(String currentOS) {
		//String mac  = TestInitializr.getSysConfigObj().getOs().getMac().getTagName();
	
		super(String.format("Current OS is "+ currentOS +". Supported OS are "+ getSupportedOSList()));
	}
	
	public static String getSupportedOSList() {
		return "{"+TestInitializr.getSysConfigObj().getOs().getWin().getTagName()+","+TestInitializr.getSysConfigObj().getOs().getMac().getTagName()+"}";
	}

}
