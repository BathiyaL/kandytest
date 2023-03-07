package com.ktsapi.mobile;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.ktsapi.core.TestInitializr;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.utils.AvtomatUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AndroidDriverManager implements MobileDriverManager{

	@Override
	public AndroidDriver get() {
		
		AppiumDriverLocalService service;
		AndroidDriver driver;
		UiAutomator2Options options;
		AndriodDriverManagerObject admObj;
		try {
			admObj = AvtomatUtils.getAndriodDriverManagerObject();
		} catch (Exception e1) {
			throw new ConfigFileNotFoundException(e1.getMessage());
		}
		
		System.out.println("Configuring Appium .......");
		
		launcEmulatorOnWindows(admObj);

		System.out.println("Starting Appium server.......");
		service = new AppiumServiceBuilder()
				.withAppiumJS(admObj.getAppiumJS()) //"C:\\Users\\stz\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"
				.usingDriverExecutable (admObj.getNodeJSExecutable())
				.withIPAddress(admObj.ipAddress)
				.usingPort(admObj.getPort())
				.withArgument (GeneralServerFlag.SESSION_OVERRIDE)
				.build();
		service.start();

		options = new UiAutomator2Options();
		options.setDeviceName(TestInitializr.getTestConfiguration().getMobileDeviceName());
		options.setChromedriverExecutable(admObj.getMobileChromeDriverPath().toString());
		options.setApp(admObj.getMobileAppsPath().resolve(TestInitializr.getTestConfiguration().getMobileApp()).toString());
		
		driver = new AndroidDriver(admObj.getAppiumServerRemoteAddress(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;

	}
	
	private void launcEmulatorOnWindows(AndriodDriverManagerObject admObj) {		
		String emulatorPath = admObj.getEmulatorEXEPath().toString() + File.separator + "emulator";
		String nameOfAVD = TestInitializr.getTestConfiguration().getMobileDeviceName(); // "Pixel_2_XL_API_33";
		System.out.println("Launching emulator '" + nameOfAVD + "' ...");
		
		String[] aCommand = new String[] { emulatorPath, "-avd",
				TestInitializr.getTestConfiguration().getMobileDeviceName() };
		try {
			Process process = new ProcessBuilder(aCommand).start();
			boolean status = process.waitFor(admObj.emulatorStartingWaitTimeInSeconds, TimeUnit.SECONDS);
			if (status) {
				System.out.println(
						"Failed to launch emulator " + nameOfAVD + " programmatically. It might already launched");
			} else {
				System.out.println("Emulator " + nameOfAVD + " launch successfully.");
			}

		} catch (Exception e) {
			System.out.println("Error ouccer while launching the emulator " + nameOfAVD + " -> " + e.getMessage());
		}
	}

}
