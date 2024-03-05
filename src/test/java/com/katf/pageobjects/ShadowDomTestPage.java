package com.katf.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.ktsapi.annotation.LocateBy;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pageobject.KandyWebPageObject;

public class ShadowDomTestPage extends KandyWebPageObject<FBPage> {

	public ShadowDomTestPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
    @LocateBy(
        	shadowLocators = {"id=shadow_host","cssSelector=#nested_shadow_host"}
        )
        @FindBy(css = "#nested_shadow_content > div")
        public EnhancedWebElement nestedText;

}
