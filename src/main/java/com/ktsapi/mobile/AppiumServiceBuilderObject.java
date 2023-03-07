package com.ktsapi.mobile;

import java.io.File;

public class AppiumServiceBuilderObject {

	File appiumJS;
	File nodeJSExecutable;
	String ipAddress;
	int port;
	
	public File getAppiumJS() {
		return appiumJS;
	}
	public void setAppiumJS(File appiumJS) {
		this.appiumJS = appiumJS;
	}
	public File getNodeJSExecutable() {
		return nodeJSExecutable;
	}
	public void setNodeJSExecutable(File nodeJSExecutable) {
		this.nodeJSExecutable = nodeJSExecutable;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
