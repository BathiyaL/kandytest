package com.ktsapi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebActons {

	public static void OpenBrowser() {
		// todo
	}
	
	public static WebDriver driver(){
		System.setProperty("webdriver.chrome.driver","C:\\selenium\\resources\\chromedriver.exe");
		WebDriver chromeDriver = new ChromeDriver();
		return chromeDriver;
	}
	
	public static void GoTo(String url) {
		driver().get(url);
	}
}
