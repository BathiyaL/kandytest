package com.katf.pageobjects;

import static com.ktsapi.WebActons.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.ktsapi.elements.EnhancedWebElement;
import com.ktsapi.pagefactory.AvtomatSeleniumDecorator;
import com.ktsapi.pageobject.KandyWebPageObject;


public class GitHubPage extends KandyWebPageObject<GitHubPage>{

	public GitHubPage(WebDriver driver) {
		super(driver);		
	}
	
	@FindBy(xpath="//input[@name='q']")
	public EnhancedWebElement searchTextBox;

	
}
