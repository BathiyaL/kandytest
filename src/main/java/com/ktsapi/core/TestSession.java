package com.ktsapi.core;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.google.common.collect.Maps;

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

		}catch (Exception ex) {
			
		}finally {
			TEST_CACHE.remove();
		}
	}
	
	private static void closeWebdriver() {
		if(isDriverUse()) {
			quiteWebDriverGracefully();
		}
	}
	
	private static void quiteWebDriverGracefully() {
		try {
			if (getWebDriver() != null) {
				//System.out.println("################## : TestSession.clsoeDriver");
				getWebDriver().quit();
			}
		} catch (Throwable ignore_dont_care) {
			// e.printStackTrace();
			// no-op
		}
	}
}
