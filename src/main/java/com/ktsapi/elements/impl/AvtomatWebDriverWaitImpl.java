package com.ktsapi.elements.impl;

import com.ktsapi.elements.AvtomatWebDriverWait;

public class AvtomatWebDriverWaitImpl implements AvtomatWebDriverWait{
	
	long timeOutInSeconds = 0;
	public AvtomatWebDriverWaitImpl(long timeOutInSeconds){
		this.timeOutInSeconds = timeOutInSeconds;
	}

	@Override
	public void until(long timeOutInSeconds) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :: " + timeOutInSeconds);
		
	}

	@Override
	public void visible() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Visible :: " + timeOutInSeconds);
		
	}

}
