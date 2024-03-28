package com.ktsapi.pagefactory;

import static com.ktsapi.WebActons.webDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AvtomatPageFactory extends PageFactory {
	
	//TODO: this should go to Actions class
	public static <C> C getWebPage(Class<C> page) {
		C c = initWebPage(page);
		initWebElements(c);
		return c;
	}
	
	private static void initWebElements(Object page){
		initElements(new AvtomatSeleniumDecorator(webDriver()), page);
	}

	private static <T> T initWebPage(Class<T> pageClassToProxy) {
		T page = instantiateWebPage(webDriver(),pageClassToProxy);
		return page;
	}

	private static <T> T instantiateWebPage(WebDriver driver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
