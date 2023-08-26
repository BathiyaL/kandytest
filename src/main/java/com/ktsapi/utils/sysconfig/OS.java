package com.ktsapi.utils.sysconfig;

public class OS {
	private String[] supportTypes;
	private Mac mac;
	private Windows win;
	
	public String[] getSupportTypes() {
		return supportTypes;
	}
	public void setSupportTypes(String[] supportTypes) {
		this.supportTypes = supportTypes;
	}
	public Mac getMac() {
		return mac;
	}
	public void setMac(Mac mac) {
		this.mac = mac;
	}
	public Windows getWin() {
		return win;
	}
	public void setWin(Windows win) {
		this.win = win;
	}
}
