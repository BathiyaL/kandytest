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
			admObj = AvtomatUtils.getAndriodDriverManagerObject();
		} catch (Exception e1) {
			throw new ConfigFileNotFoundException(e1.getMessage());
		}
		
		ConfigLogger.logInfo("Configuring Appium .......");
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
			ConfigLogger.logError("If emulator is launched manually script will run on it");
		}
	}

}
