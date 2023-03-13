package com.ktsapi.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.ktsapi.actions.core.DriverTimeOuts;
import com.ktsapi.contexts.MobileDriverDefaults;
import com.ktsapi.contexts.TestConfigurationDefaults;
import com.ktsapi.contexts.WebDriverDefaults;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface TestConfiguration {

	String baseUrl() default TestConfigurationDefaults.DEFAULT_BASE_URL;
	Browsers browser() default Browsers.CHROME;
	String browserVersion() default WebDriverDefaults.UNDEFINED;
	TestDriver testDriver();
	String[] chromeOptions() default {WebDriverDefaults.UNDEFINED};
	ExecutionMode executionMode() default ExecutionMode.DEFAULT;
	String gridHubURL() default "http://localhost:4444";
	long implicitlyWaitTime() default DriverTimeOuts.DEFAULT_IMPLICITLY_WAIT_TIME;
	long scriptTimeout() default DriverTimeOuts.DEFAULT_SCRIPT_TIMEOUT;
	long pageLoadTimeout() default DriverTimeOuts.DEFAULT_PAGELOAD_TIMEOUT;
	
	// mobile configruations
	String mobileApp() default MobileDriverDefaults.UNDEFINED;
	String mobileDeviceName() default MobileDriverDefaults.UNDEFINED;
	String mobileCapabilitiesFileName() default MobileDriverDefaults.UNDEFINED;
}
