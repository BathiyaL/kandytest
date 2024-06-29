package com.ktsapi;

import com.ktsapi.actions.CommonDriverAction;
import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.core.CommonDriverActionImpl;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.api.KandyTestAPIDriverActions;
import com.ktsapi.api.KandyTestAPIDriverActionsImpl;
import com.ktsapi.mobile.actions.KandyTestMobileDriverActions;
import com.ktsapi.mobile.actions.KandyTestMobileDriverActionsImpl;

public class KTestActonsHandler {

	public static KandyTestWebDriverActions webDriverActionsInstance() {
		return new KandyTestWebDriverActionsImpl();
	}

	public static CommonDriverAction commanDriverActionsInstance() {
		return new CommonDriverActionImpl();
	}

	public static KandyTestMobileDriverActions mobileDriverActionsInstance() {
		return new KandyTestMobileDriverActionsImpl();
	}
	
	public static KandyTestAPIDriverActions apiDriverActionsInstance() {
		return new KandyTestAPIDriverActionsImpl();
	}
}
