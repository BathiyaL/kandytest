package com.ktsapi.mobile;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.openqa.selenium.SessionNotCreatedException;

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
			admObj = AvtomatUtils.getAndriodDriverManagerObject();
		} catch (Exception e1) {
			throw new ConfigFileNotFoundException(e1.getMessage());
		}
		
		launchEmulator(admObj);
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
			throw new AndriodDriverManagerException("Error occurred while fetching mobile capabilities json file -> " + e.getMessage());
		} catch (Exception e) {
			throw new AndriodDriverManagerException("Error occurred while setting mobile capabilities -> " + e.getMessage());
		}
		
		try {
			driver = new AndroidDriver(admObj.getAppiumServerRemoteAddress(), options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}catch (SessionNotCreatedException e) {
			if(e.getMessage().contains("Could not find a connected Android device")) {
				ConfigLogger.logError("Error occurred while launching emulator make sure you have enough ritghts(e.g. admin rights) to launch emulator from the code");
			}
			if(e.getMessage().contains("Could not start a new session")) {
				ConfigLogger.logError("Error occurred while launching Android driver, make sure appium server is up and running");
			}
			throw new AndriodDriverManagerException("Error occurred while launching Android driver -> " + e.getMessage());
		}
		return driver;
	}
	
	private void startAppimServer(AndriodDriverManagerObject admObj) {
		// TODO : need to fix
		ConfigLogger.logInfo("Starting Appium server.......");
		try {
			AppiumDriverLocalService service = new AppiumServiceBuilder()
					.withAppiumJS(admObj.getAppiumJS())
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
	
	private void launchEmulator(AndriodDriverManagerObject admObj) {
		ConfigLogger.logInfo("Launching emulator .......");
		String emulatorPath = admObj.getEmulatorPath().toString();
		String nameOfAVD = TestInitializr.getTestConfiguration().getMobileDeviceName();
		ProcessBuilder processBuilder = new ProcessBuilder();
		ConfigLogger.logInfo("Launching emulator '" + nameOfAVD + "' ...");
		
		String runningOS = AvtomatUtils.getOS();
		if (runningOS.equals(TestInitializr.getSysConfigObj().getOs().getMac().getTagName())) {
			processBuilder.directory(new File(emulatorPath));
			processBuilder.command("screen","-dm","emulator", "-avd", nameOfAVD);
		}else if(runningOS.equals(TestInitializr.getSysConfigObj().getOs().getWin().getTagName())) {
			String[] winCommand = new String[] { emulatorPath, "-avd",nameOfAVD};
			processBuilder.command(winCommand);
		}else {
			throw new OSNotSupportException(runningOS);
		}
		
		try {
			Process process = processBuilder.start();
			boolean isFinished = process.waitFor(30, TimeUnit.SECONDS);
	        if(!isFinished) {
	            process.destroyForcibly();
	        }

		} catch (Exception e) {
			ConfigLogger.logError("Error ouccered while launching the emulator " + nameOfAVD + " -> " + e.getMessage());
			ConfigLogger.logInfo("If emulator is launched manually script will run on it");
		}
	}

}
