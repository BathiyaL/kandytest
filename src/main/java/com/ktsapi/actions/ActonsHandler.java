package com.ktsapi.actions;

import com.ktsapi.actions.core.CommonDriverActionImpl;
import com.ktsapi.actions.core.KTestWebDriverActionsWrapper;
import com.ktsapi.exceptions.ActionsHandlerException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class ActonsHandler implements InvocationHandler {
	
	private Object realObject;

	public ActonsHandler(Object real) {
		realObject = real;
	}

	
	public static KTestWebDriverActions webDriverActionsInstance() {
		
		KTestWebDriverActions selenideActions = (KTestWebDriverActions) Proxy.newProxyInstance(
				KTestWebDriverActions.class.getClassLoader(),
        new Class[] {KTestWebDriverActions.class}, 
        new ActonsHandler(new KTestWebDriverActionsWrapper()));
		return selenideActions;
	}

	public static CommonDriverAction commanDriverActionsInstance() {
		CommonDriverAction selenideActions = (CommonDriverAction) Proxy.newProxyInstance(
				CommonDriverAction.class.getClassLoader(),
        new Class[] {CommonDriverAction.class}, 
        new ActonsHandler(new CommonDriverActionImpl()));
		return selenideActions;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			return method.invoke(realObject, args);
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
	}
	
	
}
