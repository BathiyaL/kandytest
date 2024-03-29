package com.ktsapi.mobile;

import java.net.URL;
import java.nio.file.Path;

public class AndriodDriverManagerObject extends AppiumServiceBuilderObject{
	
	Path mobileAppsPath;
	Path mobileChromeDriverPath;
	Path emulatorPath;
	URL appiumServerRemoteAddress;
	int emulatorStartingWaitTimeInSeconds;
	
	public int getEmulatorStartingWaitTimeInSeconds() {
		return emulatorStartingWaitTimeInSeconds;
	}
	public void setEmulatorStartingWaitTimeInSeconds(int emulatorStartingWaitTimeInSeconds) {
		this.emulatorStartingWaitTimeInSeconds = emulatorStartingWaitTimeInSeconds;
	}
	public Path getEmulatorPath() {
		return emulatorPath;
	}
	public void setEmulatorPath(Path emulatorEXEPath) {
		this.emulatorPath = emulatorEXEPath;
	}	
	public URL getAppiumServerRemoteAddress() {
		return appiumServerRemoteAddress;
	}
	public void setAppiumServerRemoteAddress(URL appiumServerRemoteAddress) {
		this.appiumServerRemoteAddress = appiumServerRemoteAddress;
	}
	public Path getMobileAppsPath() {
		return mobileAppsPath;
	}
	public void setMobileAppsPath(Path mobileAppsPath) {
		this.mobileAppsPath = mobileAppsPath;
	}
	public Path getMobileChromeDriverPath() {
		return mobileChromeDriverPath;
	}
	public void setMobileChromeDriverPath(Path mobileChromeDriverPath) {
		this.mobileChromeDriverPath = mobileChromeDriverPath;
	}
}
