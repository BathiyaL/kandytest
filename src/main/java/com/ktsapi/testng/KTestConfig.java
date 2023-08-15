package com.ktsapi.testng;

public class KTestConfig {

	private boolean isDryRun;
	private String applicationId;
	
	public boolean isDryRun() {
		return isDryRun;
	}
	public void setDryRun(boolean isDryRun) {
		this.isDryRun = isDryRun;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	

}
