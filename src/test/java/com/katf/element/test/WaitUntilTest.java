package com.katf.element.test;

import static com.ktsapi.CommonActions.*;
import static com.ktsapi.WebActons.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.katf.pageobjects.WebElementPage1;
import com.ktsapi.WebActons;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		//browserVersion="74",
		chromeOptions = {"--start-maximized","--ignore-certificate-errors"}
)

public class WaitUntilTest {
    @Test
    public void testMethod(){

    	OpenBrowser();
    	GoTo("http://www.softwareautomationengineer.com/demo-site/web-elements-page-1.html");
    	GetTitle();
    	WebElementPage1 page = getWebPage(WebElementPage1.class);
    	
    	//FindElement(page.noElement.getByLocator());
    	
    	page.combo1.getTagName();
    	page.combo1.toComboBox().selectByVisibleText("Audi TT");
    	//page.combo1.type("abc");
    	WaitUntil(ExpectedConditions.visibilityOfElementLocated(page.combo1.getByLocator()), 5);
    	//WaitUntil(ExpectedConditions.elementToBeClickable(By.name("abc")), 5);
    	
//    	WebElement elm = $$(By.xpath("//select[@id='dropdown']"));
//    	WaitUntil(ExpectedConditions.elementToBeClickable(elm), 5);
    	
    	WaitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='dropdown']")), 5);
    	
    	//GoTo("https://www.google.com/");
    	print(">>>>>>>>>>>>>>>>>>>>>> :: " + GetTitle());
    	WaitUntil(ExpectedConditions.titleContains("Web"), 5);
    	
    	WaitUntil(ExpectedConditions.alertIsPresent(), 15);
    	
    	//ExpectedConditions.titleContains("Web");
    	//WaitUntil(ExpectedConditions.titleContains("123"), 5);
    	   
    	pause(5);
    	
    	
    	
    
    }

}
