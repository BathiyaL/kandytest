package com.ktsapi.contexts;

import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.ExecutionMode;
import com.ktsapi.enums.TestDriver;

public class TestConfigurationContext {

	Browsers browser;
	String browserVersion;
	private String baseUrl;
	TestDriver testDriver;
	String[] chromeOptions;
	ExecutionMode executionMode;
	String gridHubURL;
	long implicitlyWaitTime;
	long scriptTimeout;
	long pageLoadTimeout;
	
	// Mobile configurations
	String mobileApp;
	String mobileDeviceName;
	
	
	public void setBrowser(Browsers browser)
	{
		this.browser = browser;
	}
	public Browsers getBrowser()
	{
		return browser;
	}
	
	public void setBrowserVersion(String browserVersion)
	{
		this.browserVersion = browserVersion;
	}
	public String getBrowserVersion()
	{
		return browserVersion;
	}
	
	public void setTestDriver(TestDriver testDriver)
	{
		this.testDriver = testDriver;
	}
	public TestDriver getTestDriver()
	{
		return testDriver;
	}
	
	public void setChromeOptions(String[] chromeOptions)
	{
		this.chromeOptions = chromeOptions;
	}
	public String[] getChromeOptions()
	{
		return chromeOptions;
	}
	public void setExecutionMode(ExecutionMode executionMode)
	{
		this.executionMode = executionMode;
	}
	public ExecutionMode getExecutionMode()
	{
		return executionMode;
	}
	
	public void setGridHubURL(String gridHubURL)
	{
		this.gridHubURL = gridHubURL;
	}
	public String getGridHubURL()
	{
		return gridHubURL;
	}
	
	public void setImplicitlyWaitTime(long implicitlyWaitTime)
	{
		this.implicitlyWaitTime = implicitlyWaitTime;
	}
	public long getImplicitlyWaitTime()
	{
		return implicitlyWaitTime;
	}
	
	public void setScriptTimeout(long scriptTimeout)
	{
		this.scriptTimeout = scriptTimeout;
	}
	public long getScriptTimeout()
	{
		return scriptTimeout;
	}
	
	public void setPageLoadTimeout(long pageLoadTimeout)
	{
		this.pageLoadTimeout = pageLoadTimeout;
	}
	public long getPageLoadTimeout()
	{
		return pageLoadTimeout;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public String getMobileApp() {
		return mobileApp;
	}
	public void setMobileApp(String mobileApp) {
		this.mobileApp = mobileApp;
	}
	public String getMobileDeviceName() {
		return mobileDeviceName;
	}
	public void setMobileDeviceName(String mobileDeviceName) {
		this.mobileDeviceName = mobileDeviceName;
	}
	
}
