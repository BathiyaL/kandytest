package com.ktsapi.pagefactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ktsapi.elements.BaseWebElement;


public class AvtomatElementFactoryImpl implements AvtomatElementFactory{

	/**
	 * @param elementClass (e.g. Text.class interface)
	 * @return object of the element implementation (e.g. TextImpl.class)
	 */
	private <E extends BaseWebElement> Class<? extends E> findImplementingClass(final Class<E> elementClass) {
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

	/*
	 * this is used by AvtomatBotWebDriverActionsImpl.FindEnhancedWebElement()
	 * need to check do we need this
	 */
	@Override
	public <E extends BaseWebElement> E create(Class<E> elementClass, WebElement wrappedElement, By byLoctor, String fieldName) {
		E element;
		try {
			 // element implementation class (e.g. TextImpl.class) should have a constructor
			 // with WebElement and By locator constructor (e.g :: TextImpl(WebElement webelement, By byLocator))
			element = findImplementingClass(elementClass)
						.getDeclaredConstructor(WebElement.class,By.class,String.class)
						.newInstance(wrappedElement,byLoctor,fieldName);

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
	
	@Override
	public <E extends BaseWebElement> E create(Class<E> elementClass, WebElement wrappedElement, EnhancedWebElementLocator EnhancedWebElementLocator) {
		E element;
		try {
			 // element implementation class (e.g. TextImpl.class) should have a constructor
			 // with WebElement and By locator constructor (e.g :: TextImpl(WebElement webelement, By byLocator))
//			element = findImplementingClass(elementClass)
//						.getDeclaredConstructor(WebElement.class,By.class,String.class)
//						.newInstance(wrappedElement,byLoctor,fieldName);
			
			element = findImplementingClass(elementClass)
					.getDeclaredConstructor(WebElement.class,EnhancedWebElementLocator.class)
					.newInstance(wrappedElement,EnhancedWebElementLocator);
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
	
	private String getElementImplementation(String className){
		
    	try {
			Class<?> CLAZZ = Class.forName("com.ktsapi.enums.ABotElement");
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




}
