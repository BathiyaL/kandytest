package com.katf.pageobjects;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import com.ktsapi.annotation.LocateBy;
import com.ktsapi.annotation.OverrideAction;
import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pageobject.KandyWebPageObject;

public class WebElementPage1 extends KandyWebPageObject<WebElementPage1>{
	
	public WebElementPage1(WebDriver driver){
		super(driver);		
	}

	
    @FindBy(id = "submit")
    @CacheLookup
    public EnhancedWebElement clickMe1;

    @FindBy(id = "submit")
    @CacheLookup
    public EnhancedWebElement clickMe2;

    @FindBy(id = "dropdown")
    @CacheLookup
    public EnhancedWebElement combo1;

    @FindBy(id = "dropdown2")
    @CacheLookup
    public EnhancedWebElement combo2;

    @FindBy(id = "double-click-text-2")
    @CacheLookup
    public EnhancedWebElement link;

    @FindBy(id = "opt1")
    @CacheLookup
    public EnhancedWebElement option11;

    @FindBy(name = "optradio")
    @CacheLookup
    private List<WebElement> option12;

    @FindBy(id = "2_opt1")
    @CacheLookup
    public EnhancedWebElement option13;

    @FindBy(name = "optradio2")
    @CacheLookup
    private List<WebElement> option14;

    private final String option1Value = "on";

    private final String option12Value = "on";

    @FindBy(id = "opt2")
    @CacheLookup
    public EnhancedWebElement option21;

    @FindBy(name = "optradio")
    @CacheLookup
    private List<WebElement> option22;

    @FindBy(id = "2_opt2")
    @CacheLookup
    public EnhancedWebElement option23;

    @FindBy(name = "optradio2")
    @CacheLookup
    private List<WebElement> option24;

    private final String option21Value = "on";

    private final String option22Value = "on";

    @FindBy(name = "optradio")
    @CacheLookup
    private List<WebElement> option31;

    @FindBy(name = "optradio2")
    @CacheLookup
    private List<WebElement> option32;

    @FindBy(id = "opt3")
    @CacheLookup
    public EnhancedWebElement option3Disabled1;

    @FindBy(id = "2_opt3")
    @CacheLookup
    public EnhancedWebElement option3Disabled2;

    private final String option31Value = "on";

    private final String option32Value = "on";

    private final String pageLoadedText = "Web Elements (not in visible area)";

    private final String pageUrl = "/demo-site/web-elements-page-1.html";

    @FindBy(id = "password")
    @CacheLookup
    public EnhancedWebElement password;

    @FindBy(id = "email")
    @CacheLookup
    public EnhancedWebElement textArea1;

    @FindBy(id = "comment")
    @CacheLookup
    public EnhancedWebElement textArea2;

    @FindBy(id = "email")
    @CacheLookup
    public EnhancedWebElement textArea3;

    @FindBy(id = "password2")
    @CacheLookup
    public EnhancedWebElement textArea4;

    @FindBy(id = "comment2")
    @CacheLookup
    public EnhancedWebElement textArea5;

    @FindBy(id = "double-click-text-1")
    @CacheLookup
    public EnhancedWebElement textdoubleClick;

    @FindBy(css = "div:nth-of-type(1) div:nth-of-type(1) div.col-md-12 div:nth-of-type(2) div:nth-of-type(3) div:nth-of-type(1) form a")
    @CacheLookup
    public EnhancedWebElement web1;

    @FindBy(css = "div:nth-of-type(1) div:nth-of-type(3) div:nth-of-type(3) div.well form a")
    @CacheLookup
    public EnhancedWebElement web2;
    
    @FindBy(linkText = "Web")
    @CacheLookup
    public EnhancedWebElement webLink1;
    
    
    @FindBy(name = "clickMeButton2")
    @CacheLookup
    public EnhancedWebElement clickMeButton2;

    
    @LocateBy
    @FindBy(name = "enterText2")
    public EnhancedWebElement playEmailTextBoxElm;
    
    @LocateBy(
       //windowHandles = {"a","b"},
        frames = {"name=frame2"},
        targets={"linkText=Web Elements Examples","css=.alert:nth-child(1) > a","id=element","name=elm"}    	
    )
    @FindBy(name = "color")
    public EnhancedWebElement playColorElm;
    
    @LocateBy(
    	//windowHandles = {"a","b"},
    	frames = {"name=frame2","xpath=//iframe[@src='inner-frame.html']"},
    	//frames = {"names=frame2","index=1"},
    	//frames = {"names:frame2","index=1"},
    	//frames = {"names=","index=1"},
    	targets={"name=size3","xpath=Web Elements Examples","id=.alert:nth-child(1) > a","id=element","name=elm"}    	
    )
    @FindBy(name = "size")
    public EnhancedWebElement playSizeElm;
    
    @LocateBy(
    		//type=Type.Dynamic,
        	//windowHandles = {"a","b"},
        	frames = {"name=iframe-1","xpath=//iframe[@src='web-element-inner-frame.html']"},
        	//frames = {"names=frame2","index=1"},
        	//frames = {"names:frame2","index=1"},
        	//frames = {"names=","index=1"},
        	targets={"name=size3","xpaths=Web Elements Examples","id=iframe2_comment","id=element"}    	
        )
     @FindBy(name = "size2")
     public EnhancedWebElement playSizeElm2;
    
    
    @FindBy(id = "noelement")
    @CacheLookup
    public EnhancedWebElement noElement;
 
    
    @FindBy(xpath = "//a[contains(@data-testid,'login')]")
    @CacheLookup
    public EnhancedWebElement TwitterElm;
}
