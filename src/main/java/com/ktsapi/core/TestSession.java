package com.ktsapi.core;

import java.util.Map;
import com.google.common.collect.Maps;
import com.ktsapi.actions.core.ConfigLogger;

public class TestSession extends TestInitializr{
	
	/*
	 * Created TestCache instance
	 */
	public static void open(TestContext context) {
		Map<String, Object> map = Maps.newHashMap();
		//TODO : set relevant values to map
		/*
		 * here need to init TEP level data since those common to all tests and need to call only one time for entire TEP.
		 * TEP UUID
		 */
		TEST_CACHE.set(map); // Initialize the TEST_CACHE
	}
	
	public static void close() {
		try {
			closeWebdriver();
			closeMobileDriver();

		}catch (Exception ex) {
			
		}finally {
			TEST_CACHE.remove();
		}
	}
	
	private static void closeWebdriver() {
		if(isWebDriverUse()) {
			quiteWebDriverGracefully();
		}
	}
	private static void closeMobileDriver() {
		if(isMobileDriverUse()) {
			quiteMobileDriverGracefully();
		}
	}
	
	private static void quiteWebDriverGracefully() {
		try {
			if (getWebDriver() != null) {
				getWebDriver().quit();
			}
		} catch (Throwable ex) {
			ConfigLogger.logInfo("[TestSession]-> Error occurred while quitting web-driver: " + ex.getMessage());
		}
	}
	private static void quiteMobileDriverGracefully() {
		try {
			if (getMobileDriver() != null) {
				getMobileDriver().quit();
			}
		} catch (Throwable ex) {
			ConfigLogger.logInfo("[TestSession]-> Error occurred while quitting mobile-driver : " + ex.getMessage());
		}
	}
}
