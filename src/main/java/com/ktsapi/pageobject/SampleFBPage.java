package com.ktsapi.pageobject;

import static com.ktsapi.WebActons.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pagefactory.AvtomatSeleniumDecorator;

public class SampleFBPage extends KandyWebPageObject<SampleFBPage>{

	public SampleFBPage(WebDriver driver) {
		super(driver);
		//PageFactory.initElements(new AvtomatSeleniumDecorator(driver()), this);
	}

	
	@FindBy(name="firstname")
	public WebElement webElm;

	
	@FindBy(name="firstname")
	public EnhancedWebElement abotElm;
	
	@FindBy(name="websubmit")
	public EnhancedWebElement createAccountButton2;

}
