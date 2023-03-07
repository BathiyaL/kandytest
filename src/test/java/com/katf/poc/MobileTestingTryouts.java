package com.katf.poc;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MobileTestingTryouts {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//runEmulatorProgrammatically();
		method2();
	}

	public static void runEmulatorProgrammatically() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("emulator", "-avd", "Pixel_2_XL_API_33"); //emulator -avd Pixel_2_XL_API_33
		pb.redirectErrorStream(true);
		Process process = pb.start();

		// Wait for the process to finish
		int exitCode = process.waitFor();

		// Check the exit code to see if the emulator started successfully
		if (exitCode == 0) {
		    System.out.println("Emulator started successfully.");
		} else {
		    System.out.println("Emulator failed to start.");
		}

	}
	
	public static void method2() {
		
		String sdkPath = "C:\\Users\\stz\\AppData\\Local\\Android\\Sdk\\emulator";
		//String adbPath = sdkPath + "platform-tools" + File.separator + "adb";
		String emulatorPath = sdkPath + File.separator + "emulator";
		String nameOfAVD = "Pixel_2_XL_API_33";
		System.out.println(emulatorPath);
		System.out.println("Starting emulator for '" + nameOfAVD + "' ...");
		 String[] aCommand = new String[] { emulatorPath, "-avd", nameOfAVD };
		 try {
		  Process process = new ProcessBuilder(aCommand).start();
		  
		  System.out.println("Starting ..........................1");
		  boolean status = process.waitFor(100, TimeUnit.SECONDS);
		  System.out.println(status);
		  
//		  int exitCode = process.waitFor();
//		  System.out.println(exitCode);
		  System.out.println("Emulator launched successfully!");
		 } catch (Exception e) {
		  e.printStackTrace();
		 }
	}
	
	
	private static String sdkPath = "/Applications/adt-bundle-mac-x86_64-20140702/sdk/";
	private static String adbPath = sdkPath + "platform-tools" + File.separator + "adb";
	/**
	 * Kills all running emulators
	 * http://aksahu.blogspot.com/2016/01/stop-or-kill-android-emulator.html
	 */
	public static void closeEmulator() {
	 System.out.println("Killing emulator...");
	 String[] aCommand = new String[] { adbPath, "emu", "kill" };
	 try {
	  Process process = new ProcessBuilder(aCommand).start();
	  process.waitFor(1, TimeUnit.SECONDS);
	  System.out.println("Emulator closed successfully!");
	 } catch (Exception e) {
	  e.printStackTrace();
	 }
	}
}
