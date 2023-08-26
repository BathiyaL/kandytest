package com.ktsapi.webdrivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriverProvider extends KandyWebDriverManager {

	// TODO : WIP
	@Override
	public WebDriver get() {
	    SafariDriver driver;
		SafariOptions options = new SafariOptions();
        driver = new SafariDriver(options);
        
        return driver;
	}

}
