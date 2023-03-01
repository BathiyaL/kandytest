package com.ktsapi.mobile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;

import com.ktsapi.pagefactory.EnhancedWebElementLocator;

public class KMobileElementFactoryImpl {

	private <E extends BaseMobileElement> Class<? extends E> findImplementingClass(final Class<E> elementClass) {
		String pack = elementClass.getPackage().getName();
		String interfaceClassName = elementClass.getSimpleName();
		String interfaceClassFullName = pack + "." + interfaceClassName;		
		String  implementationClassName = getElementImplementation(interfaceClassName);
		
		if (implementationClassName == null)
			throw new RuntimeException("No implementation found for interface " + interfaceClassFullName);
		try {
			return (Class<? extends E>) Class.forName(implementationClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load class for " + implementationClassName, e);
		}
	}
	
	private String getElementImplementation(String className){
		
    	try {
			Class<?> CLAZZ = Class.forName("com.ktsapi.enums.ABotElement"); // enum contains impl class for the requested element class
			Object[] consts = CLAZZ.getEnumConstants();
			String attributeValue = null;

					for (Object obj : consts) {
						if (obj.toString().equals(className)) {
							Class<?> sub = obj.getClass();
							Method mth = sub.getDeclaredMethod("getElementImpl");
							attributeValue = mth.invoke(obj).toString();	
							return attributeValue;
						}
					}			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ABotElement enum class not found");			
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	public <E extends BaseMobileElement> E create(Class<E> elementClass, WebElement wrappedElement, EnhancedMobileElementLocator enhancedMobileElementLocator) {
		E element;
		try {
			 // element implementation class (e.g. TextImpl.class) should have a constructor
			 // with WebElement and By locator constructor (e.g :: TextImpl(WebElement webelement, By byLocator))
//			element = findImplementingClass(elementClass)
//						.getDeclaredConstructor(WebElement.class,By.class,String.class)
//						.newInstance(wrappedElement,byLoctor,fieldName);
			//System.out.print("+++++++++++++++++++++++++++++++++++++++++++elementClass.getName() ::: " + elementClass.getName());
			/*
			 * e.g. getting EnhancedMobileElement.clas, and find corresponding impl class
			 */
			element = findImplementingClass(elementClass)
					.getDeclaredConstructor(WebElement.class,EnhancedMobileElementLocator.class)
					.newInstance(wrappedElement,enhancedMobileElementLocator);
		}

		catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}

		return element;
	}

	
}
