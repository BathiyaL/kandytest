package com.ktsapi.mobile;

import org.openqa.selenium.By;

public class EnhancedMobileElementLocator {

	String fieldName;
	By target; // target locator defined under FindBy annotation
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public By getTarget() {
		return target;
	}
	public void setTarget(By target) {
		this.target = target;
	}
}
