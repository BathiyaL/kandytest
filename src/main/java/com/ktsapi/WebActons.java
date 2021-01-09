package com.ktsapi;

import com.ktsapi.actions.ActonsHandler;
import com.ktsapi.actions.KTestWebDriverActions;

public class WebActons {

	public static void OpenBrowser() {
		webActonsWrapper().OpenBrowser();
	}
		
	public static KTestWebDriverActions webActonsWrapper(){
		return ActonsHandler.webDriverActionsInstance();
	}
	
	public static void GoTo(String url) {
		webActonsWrapper().GoTo(url);
	}
}
