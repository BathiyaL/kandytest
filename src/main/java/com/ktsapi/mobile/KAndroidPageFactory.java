package com.ktsapi.mobile;

import static com.ktsapi.MobileActions.mobileDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class KAndroidPageFactory extends PageFactory{

	//TODO: this should go to Actions class
	public static <C> C getWebPage(Class<C> page) {
		C c = initMobilePage(page);
		initmobileElements(c);
		return c;
	}
	 
	private static void initmobileElements(Object page){
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>>"+page.getClass().getCanonicalName());
		//initElements(new AppiumFieldDecorator(mobileDriver()), page);
//		initElements(new AppiumFieldDecorator(mobileDriver()), page);
		initElements(new KMobileFieldDecorator(mobileDriver()), page);
	}

	private static <T> T initMobilePage(Class<T> pageClassToProxy) {
		T page = instantiateWebPage(mobileDriver(),pageClassToProxy);
		return page;
	}

	private static <T> T instantiateWebPage(AndroidDriver driver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(AndroidDriver.class);
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
