/**
 * 
 */
package com.ktsapi.actions;

/**
 * [...]  
 *
 * @author  Bathiya L.
 * @version 1.0
 */
public interface CommonDriverAction {
	void pause(int timeOut) throws InterruptedException;
	void print(String message);
	String baseUrl();
}
