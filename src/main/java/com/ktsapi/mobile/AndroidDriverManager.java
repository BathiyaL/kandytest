package com.ktsapi.mobile;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.ktsapi.actions.core.ConfigLogger;
import com.ktsapi.core.TestInitializr;
import com.ktsapi.exceptions.AndriodDriverManagerException;
import com.ktsapi.exceptions.ConfigFileNotFoundException;
import com.ktsapi.exceptions.OSNotSupportException;
import com.ktsapi.utils.AvtomatUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


public class AndroidDriverManager implements MobileDriverManager{

	@Override
	public AndroidDriver get() {
		
		AndroidDriver driver;
		UiAutomator2Options options;
		AndriodDriverManagerObject admObj;
		try {
			admObj = AvtomatUtils.getAndriodDriverManagerObject();// TODO: currently getting from ktestconfig.properties need to move to a config json
		} catch (Exception e1) {
			throw new ConfigFileNotFoundException(e1.getMessage());
		}
		
		ConfigLogger.logInfo("Configuring Appium .......");
		String runningOS = AvtomatUtils.getOS();
		if (runningOS.equals(TestInitializr.getSysConfigObj().getOs().getMac().getTagName())) {
			launcEmulatorOnMac(admObj);
		}else if(runningOS.equals(TestInitializr.getSysConfigObj().getOs().getWin().getTagName())) {
			launcEmulatorOnWindows(admObj);
		}else {
			throw new OSNotSupportException(runningOS);
		}

		startAppimServer(admObj);

		options = new UiAutomator2Options();
		options.setDeviceName(TestInitializr.getTestConfiguration().getMobileDeviceName());
		options.setChromedriverExecutable(admObj.getMobileChromeDriverPath().toString());
		options.setApp(admObj.getMobileAppsPath().resolve(TestInitializr.getTestConfiguration().getMobileApp()).toString());

		JSONObject jObj2;
		try {
			jObj2 = AvtomatUtils.getMobileCapabilitiesFile();
			for(Object key : jObj2.keySet()) {
				Object valueObj = jObj2.get(key);
				options.setCapability(String.valueOf(key).trim(), valueObj);
			}
		} catch (AndriodDriverManagerException e) {
			throw new AndriodDriverManagerException("Error occur whild fetching mobile capabilities json file -> " + e.getMessage());
		} catch (Exception e) {
			throw new AndriodDriverManagerException("Error occur whild setting mobile capabilities -> " + e.getMessage());
		}
		
		driver = new AndroidDriver(admObj.getAppiumServerRemoteAddress(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		return driver;

	}
	
	private void startAppimServer(AndriodDriverManagerObject admObj) {
		// TODO
		ConfigLogger.logInfo("Starting Appium server.......");
		try {
			AppiumDriverLocalService service = new AppiumServiceBuilder()
					.withAppiumJS(admObj.getAppiumJS()) //"C:\\Users\\stz\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"
					.usingDriverExecutable (admObj.getNodeJSExecutable())
					.withIPAddress(admObj.ipAddress)
					.usingPort(admObj.getPort())
					.withArgument (GeneralServerFlag.SESSION_OVERRIDE)
					.build();
			service.start();
		}catch(Exception ex) {
			ConfigLogger.logError("Appium server starting error! If appium server is started manually script will run else script will fail.");
		}
	}
	
	private void launcEmulatorOnMac(AndriodDriverManagerObject admObj) {	
		// TODO : To be implemented.
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
