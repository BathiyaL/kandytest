package com.ktsapi.core;

public interface Runner {
	void start();

	void end();
	
	TestContext getTestContext();
}
