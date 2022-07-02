package com.ktsapi.elements;

import com.ktsapi.annotation.ActionImplements;

@ActionImplements(name=org.openqa.selenium.support.ui.ExpectedConditions.class)
public interface ExpectedConditions {
	
	void invisibilityOfElementLocated();

}
