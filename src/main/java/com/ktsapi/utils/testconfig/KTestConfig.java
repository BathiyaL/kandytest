package com.ktsapi.utils.testconfig;

public class KTestConfig {
	private boolean isDryRun;
	private String applicationId;
	private WebDrivers webDrivers;
	
	public WebDrivers getWebDrivers() {
		return webDrivers;
	}
	public void setWebDrivers(WebDrivers webDrivers) {
		this.webDrivers = webDrivers;
	}
	public boolean getIsDryRun() {
		return isDryRun;
	}
	public void setIsDryRun(boolean isDryRun) {
		this.isDryRun = isDryRun;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}