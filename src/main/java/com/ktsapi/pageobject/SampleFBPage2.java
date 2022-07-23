package com.ktsapi.pageobject;

import static com.ktsapi.WebActons.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.ktsapi.elements.EnhancedWebElement;


//import com.ktsapi.pagefactory.AvtomatSeleniumDecorator;
//import com.seleniumcaller.elements.TextActions;

public class SampleFBPage2 extends KandyWebPageObject<SampleFBPage2>{

	public SampleFBPage2(WebDriver driver) {
		super(driver);
		//PageFactory.initElements(new AvtomatSeleniumDecorator(driver()), this);
	}
	
//	public void init(){
//		SampleFBPage page = new SampleFBPage(driver());
//		PageFactory.initElements(new AvtomatSeleniumDecorator(driver()),page );
//	}
	
	@FindBy(name="firstname")
	public EnhancedWebElement username;
	
	@FindBy(id="firstname1")
	public EnhancedWebElement username1;
	
	@FindBy(name="firstname2")
	public EnhancedWebElement username2;
	
	@FindBy(how = How.ID_OR_NAME , using = "firstname3")
	public EnhancedWebElement username3;
	
	@FindBy(how = How.CSS , using = "firstname4")
	public EnhancedWebElement username4;
	
	@FindBy(how = How.PARTIAL_LINK_TEXT , using = "firstname5")
	public EnhancedWebElement username5;
	
	@FindBy(name="firstname")
	public WebElement webElm;
	
	@FindBy(name="firstname")
	public EnhancedWebElement abotElm;
	
	@FindBy(name="websubmit")
	public EnhancedWebElement createAccountButton2;
	
	@FindBy(name="noElement")
	public EnhancedWebElement noElement;
	
	
	@FindBy(id="reg_form_box")
	public EnhancedWebElement reg_form_box;
	
	//emo page....................
	
	@FindBy(name="submitButton")
	public EnhancedWebElement submitButton;
	
	@FindBy(name="emaiTextBox")
	public EnhancedWebElement emaiTextBox;
	
	@FindBy(id="password")
	public EnhancedWebElement password;
	
	@FindBy(name="websubmit")
	public EnhancedWebElement signUp;
	
	
	@FindBy(id="rememberMe")
	public EnhancedWebElement rememberMe;	

}
