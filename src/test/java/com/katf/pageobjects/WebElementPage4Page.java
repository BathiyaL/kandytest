package com.katf.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pageobject.KandyWebPageObject;

public class WebElementPage4Page extends KandyWebPageObject<WebElementPage4Page> {
	
	public final String PAGE_TITLE = "Sample Elmments";
	
	public WebElementPage4Page(WebDriver driver){
		super(driver);		
	}	

	@FindBy(name="Name")
	public EnhancedWebElement name;
	
	@FindBy(name="Email")
	public EnhancedWebElement email;
	
	@FindBy(name="button")
	public EnhancedWebElement button;
	
	
	@FindBy(id="lbltipAddedComment")
	public EnhancedWebElement lbltipAddedComment;
	
	@FindBy(xpath="//input[@value='checkbox1']")
	public EnhancedWebElement checkbox1;
	
	@FindBy(xpath="//input[@value='radio1']")
	public EnhancedWebElement radio1;
	
	@FindBy(id="cars")
	public EnhancedWebElement selectCars;
	
	@FindBy(name="multi-select")
	public EnhancedWebElement multiSelect;
	
	@FindBy(id="drag1")
	public EnhancedWebElement drag1;
	
	@FindBy(id="div1")
	public EnhancedWebElement rectangle;
	
	
	@FindBy(xpath="//*[@id='credit2']/a")
	public EnhancedWebElement source;
	
	@FindBy(xpath="//*[@id='bank']/li")
	public EnhancedWebElement destination;

}
	