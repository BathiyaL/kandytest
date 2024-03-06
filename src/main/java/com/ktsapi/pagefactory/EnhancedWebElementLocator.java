package com.ktsapi.pagefactory;

import org.openqa.selenium.By;

public class EnhancedWebElementLocator {
	
	String fieldName;
	By target; // target locator defined under FindBy annotation
	boolean isLocateByExist;
	
	String[] windowHandles;
	String[] frames;	
	String[] targets;
	String[] shadowLocators;
	
	public String[] getShadowLocators() {
		return shadowLocators;
	}
	public void setShadowLocators(String[] shadowLocators) {
		this.shadowLocators = shadowLocators;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String[] getWindowHandles() {
		return windowHandles;
	}
	public void setWindowHandles(String[] windowHandles) {
		this.windowHandles = windowHandles;
	}
	
	public String[] getFrames() {
		return frames;
	}
	public void setFrames(String[] frames) {
		this.frames = frames;
	}
			
	public By getTarget() {
		return target;
	}
	public void setTarget(By target) {
		this.target = target;
	}
	public String[] getTargets() {
		return targets;
	}
	public void setTargets(String[] targets) {
		this.targets = targets;
	}
	public boolean isLocateByExist() {
		return isLocateByExist;
	}
	public void setLocateByExist(boolean isLocateByExist) {
		this.isLocateByExist = isLocateByExist;
	}

	
	
}
