package com.ktsapi.utils.testconfig;

public class MobileDrivers {
	private String appiumJSPath;
	private String nodeJSPath;
	private String appiumServerIPAddress;
	private int appiumServerIPort;
	private String mobileAppsPath;
	private String mobileChromeDriverPath;
	private String emulatorsPath;
	private int emulatorStartingWaitTimeInSeconds;
	
	
	public String getMobileChromeDriverPath() {
		return mobileChromeDriverPath;
	}
	public void setMobileChromeDriverPath(String mobileChromeDriverPath) {
		this.mobileChromeDriverPath = mobileChromeDriverPath;
	}
	public String getAppiumJSPath() {
		return appiumJSPath;
	}
	public void setAppiumJSPath(String appiumJSPath) {
		this.appiumJSPath = appiumJSPath;
	}
	public String getNodeJSPath() {
		return nodeJSPath;
	}
	public void setNodeJSPath(String nodeJSPath) {
		this.nodeJSPath = nodeJSPath;
	}
	public String getAppiumServerIPAddress() {
		return appiumServerIPAddress;
	}
	public void setAppiumServerIPAddress(String appiumServerIPAddress) {
		this.appiumServerIPAddress = appiumServerIPAddress;
	}
	public int getAppiumServerIPort() {
		return appiumServerIPort;
	}
	public void setAppiumServerIPort(int appiumServerIPort) {
		this.appiumServerIPort = appiumServerIPort;
	}
	public String getMobileAppsPath() {
		return mobileAppsPath;
	}
	public void setMobileAppsPath(String mobileAppsPath) {
		this.mobileAppsPath = mobileAppsPath;
	}
	public String getEmulatorsPath() {
		return emulatorsPath;
	}
	public void setEmulatorsPath(String emulatorsPath) {
		this.emulatorsPath = emulatorsPath;
	}
	public int getEmulatorStartingWaitTimeInSeconds() {
		return emulatorStartingWaitTimeInSeconds;
	}
	public void setEmulatorStartingWaitTimeInSeconds(int emulatorStartingWaitTimeInSeconds) {
		emulatorStartingWaitTimeInSeconds = emulatorStartingWaitTimeInSeconds;
	}
}
