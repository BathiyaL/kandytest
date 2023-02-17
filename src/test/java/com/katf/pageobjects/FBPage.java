package com.katf.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pageobject.KandyWebPageObject;

public class FBPage extends KandyWebPageObject<FBPage> {
	
	public final String PAGE_URL = "https://www.facebook.com/";
	public final String PAGE_TITLE = "Facebook – log in or sign up";
	
	public FBPage(WebDriver driver){
		super(driver);		
	}	

//	public String getPageTitle() {		
//		return PAGE_TITLE;
//	}	
//
//	public String getPageURL() {		
//		return PAGE_URL;
//	}
	
	@FindBy(id="email")
	public EnhancedWebElement username;
	
	@FindBy(id="pass")
	public EnhancedWebElement password;
	
	@FindBy(name ="login")
	public EnhancedWebElement submit;

}
