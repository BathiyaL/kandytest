package com.ktsapi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.ktsapi.actions.CommonDriverAction;
import com.ktsapi.actions.KandyTestWebDriverActions;
import com.ktsapi.actions.core.CommonDriverActionImpl;
import com.ktsapi.actions.core.KandyTestWebDriverActionsImpl;
import com.ktsapi.exceptions.ActionsHandlerException;
import com.ktsapi.exceptions.TestClassNotFoundException;

public class ABotActonsHandler implements InvocationHandler {
	private Object realObject;

	public ABotActonsHandler(Object real) {
		realObject = real;
	}
	
	public Object invoke(Object target, Method method, Object[] arguments){

		
//		if ("execute".equals(method.getName())) {
//			// intercept method named "execute"
//			System.out.println("intercept method named execute ");
//			// return null;
//		}

		try {
			return method.invoke(realObject, arguments);
		} catch (IllegalAccessException e) {
			if(e.getCause()!=null) {
				throw new ActionsHandlerException(e.getCause().toString());	
			}
			throw new ActionsHandlerException(e.toString());	
		} catch (IllegalArgumentException e) {
			if(e.getCause()!=null) {
				throw new ActionsHandlerException(e.getCause().toString());	
			}
			throw new ActionsHandlerException(e.toString());	
		} catch (InvocationTargetException e) {
			if(e.getCause()!=null) {
				throw new ActionsHandlerException(e.getCause().toString());	
			}
			throw new ActionsHandlerException(e.toString());	
		}
		
		//throw new ActionsHandlerException("Error occur while invoke " + method.getName());
	}

	static public Object newInstance(Object obj, Class[] interfaces) {
		return java.lang.reflect.Proxy.newProxyInstance(
													obj.getClass().getClassLoader(), 
													interfaces,
													new ABotActonsHandler(obj));
	}
	
	public static KandyTestWebDriverActions newInstance() {
		KandyTestWebDriverActions selenideActions = (KandyTestWebDriverActions) Proxy.newProxyInstance(
				KandyTestWebDriverActions.class.getClassLoader(),
        new Class[] {KandyTestWebDriverActions.class}, 
        new ABotActonsHandler(new KandyTestWebDriverActionsImpl()));
		return selenideActions;
	}
	
	public static KandyTestWebDriverActions webDriverActionsInstance() {
		KandyTestWebDriverActions selenideActions = (KandyTestWebDriverActions) Proxy.newProxyInstance(
				KandyTestWebDriverActions.class.getClassLoader(),
        new Class[] {KandyTestWebDriverActions.class}, 
        new ABotActonsHandler(new KandyTestWebDriverActionsImpl()));
		return selenideActions;
	}
	
	public static CommonDriverAction commanDriverActionsInstance() {
		CommonDriverAction selenideActions = (CommonDriverAction) Proxy.newProxyInstance(
				CommonDriverAction.class.getClassLoader(),
        new Class[] {CommonDriverAction.class}, 
        new ABotActonsHandler(new CommonDriverActionImpl()));
		return selenideActions;
	}
}
