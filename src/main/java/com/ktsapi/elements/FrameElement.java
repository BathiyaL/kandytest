package com.ktsapi.elements;

public interface FrameElement extends BaseWebElement {

	public void switchToFrame();
	public void switchToParentFrame();
	public void switchToDefaultContent();
}
