package com.ktsapi.utils.testconfig;

public class WebDrivers {
	private Chrome chrome;
	private Firefox firefox;
	private int findElementTries;
	private boolean isEnabledAutoFrameSwitch=true;
	
	public boolean isEnabledAutoFrameSwitch() {
		return isEnabledAutoFrameSwitch;
	}
	public void setEnabledAutoFrameSwitch(boolean isEnabledAutoFrameSwitch) {
		this.isEnabledAutoFrameSwitch = isEnabledAutoFrameSwitch;
	}
	public Chrome getChrome() {
		return chrome;
	}
	public void setChrome(Chrome chrome) {
		this.chrome = chrome;
	}
	public int getFindElementTries() {
		return findElementTries;
	}
	public void setFindElementTries(int findElementTries) {
		this.findElementTries = findElementTries;
	}
	public Firefox getFirefox() {
		return firefox;
	}
	public void setFirefox(Firefox firefox) {
		this.firefox = firefox;
	}
}
