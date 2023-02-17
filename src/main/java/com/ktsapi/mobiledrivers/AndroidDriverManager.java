package com.ktsapi.mobiledrivers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;
import com.ktsapi.core.TestInitializr;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AndroidDriverManager implements MobileDriverManager{

	@Override
	public AndroidDriver get() {
		
		AppiumDriverLocalService service;
		AndroidDriver driver;
		UiAutomator2Options options;
		
		System.out.println("Configurating Appium .......");
		
		// starting appium server as a service from program instead explicitly starting
		
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C:\\Users\\stz\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress("127.0.0.1")
				.usingPort(4723)
				.build();
		service.start();

		options = new UiAutomator2Options();
		options.setDeviceName(TestInitializr.getTestConfiguration().getMobileDeviceName());
		options.setChromedriverExecutable("D:\\BTS\\devdesk\\workspaces\\appium-automation-demo\\appium-automation-demo\\src\\test\\java\\resources\\chromedriver_241.exe");
		//options.setApp("D:\\BTS\\devdesk\\workspaces\\appium-automation-demo\\appium-automation-demo\\src\\test\\java\\resources\\ApiDemos-debug.apk");
		options.setApp("D:\\BTS\\devdesk\\workspaces\\appium-automation-demo\\appium-automation-demo\\src\\test\\java\\resources\\" + TestInitializr.getTestConfiguration().getMobileApp());
		
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			return driver;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
		
	}

}
