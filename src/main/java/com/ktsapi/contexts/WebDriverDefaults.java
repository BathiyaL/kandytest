package com.ktsapi.contexts;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class WebDriverDefaults {

	//public static final String[] CHROME_OPTIONS = {"--start-maximized","--ignore-certificate-errors"};
	public static final String UNDEFINED = "UNDEFINED";
	public static final String BUILT_IN_BROWSER_VERSION = "UNDEFINED";
	public static final String BUILT_IN_CHROME_DRIVER_FILE_NAME_WIN =  "chromedriver.exe";
	public static final String BUILT_IN_CHROME_DRIVER_FILE_NAME_MAC =  "chromedriver";
	public static final String BUILT_IN_FIREFOX_DRIVER_FILE_NAME =  "geckodriver.exe";
	public static final List<String> CHROME_OPTIONS = ImmutableList.<String>builder()
			.add("--start-maximized")
			.add("--disable-webgl")
			.add("--blacklist-webgl")
			.add("--blacklist-accelerated-compositing")
			.add("--disable-accelerated-2d-canvas")
			.add("--disable-accelerated-compositing")
			.add("--disable-accelerated-layers")
			.add("--disable-accelerated-plugins")
			.add("--disable-accelerated-video")
			.add("--disable-accelerated-video-decode")
			.add("--disable-gpu")
			.add("--disable-infobars")
			.add("--test-type")
			.build();
	
	public static final List<String> CHROME_HEADLESS_OPTIONS = ImmutableList.<String>builder()
			.add("--no-sandbox")
			.add("--disable-dev-shm-usage")
			.add("--headless")
			.build();
	
	public static final List<String> FIREFOX_HEADLESS_OPTIONS = ImmutableList.<String>builder()
			.add("-headless")
			.build();
	
	public static final List<String> FIREFOX_OPTIONS = ImmutableList.<String>builder()
			.add("-dummy-option")
			.build();
}
