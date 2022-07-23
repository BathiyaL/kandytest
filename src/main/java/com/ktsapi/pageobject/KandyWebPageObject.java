package com.ktsapi.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import com.ktsapi.pagefactory.AvtomatSeleniumDecorator;

public class KandyWebPageObject<B extends KandyWebPageObject<B>> extends LoadableComponent<B>{
	private WebDriver driver;
//	public String pageURL ;
//	public String pageTitle;
	
	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		
	}
	
	public KandyWebPageObject(WebDriver driver) {
		this.driver = driver;
		//PageFactory.initElements(new AvtomatSeleniumDecorator(driver), this);
	}

    public WebDriver getDriver() {
    	return driver;
    }
    
//    public void setPageURL(String pageURL) {
//    	pageURL = this.pageURL;
//    }
//    public String getPageURL() {
//    	return pageURL;
//    }
//    
//    public void setPageTitle(String pageTitle) {
//    	pageTitle = this.pageTitle;
//    }
//    public String getPageTitle() {
//    	return pageTitle;
//    }

}
