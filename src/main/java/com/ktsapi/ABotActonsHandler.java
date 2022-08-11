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

public class ABotActonsHandler implements InvocationHandler {
	private Object realObject;

	public ABotActonsHandler(Object real) {
		realObject = real;
	}

	public Object invoke(Object target, Method method, Object[] arguments) {

		try {
			return method.invoke(realObject, arguments);
		} catch (IllegalAccessException e) {
			if (e.getCause() != null) {
				throw new ActionsHandlerException(e.getCause().toString());
			}
			throw new ActionsHandlerException(e.toString());
		} catch (IllegalArgumentException e) {
			if (e.getCause() != null) {
				throw new ActionsHandlerException(e.getCause().toString());
			}
			throw new ActionsHandlerException(e.toString());
		} catch (InvocationTargetException e) {
			if (e.getCause() != null) {
				throw new ActionsHandlerException(e.getCause().toString());
			}
			throw new ActionsHandlerException(e.toString());
		}
	}

	static public Object newInstance(Object obj, Class[] interfaces) {
		return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), interfaces,
				new ABotActonsHandler(obj));
	}

	public static KandyTestWebDriverActions newInstance() {
		return (KandyTestWebDriverActions) Proxy.newProxyInstance(KandyTestWebDriverActions.class.getClassLoader(),
				new Class[] { KandyTestWebDriverActions.class },
				new ABotActonsHandler(new KandyTestWebDriverActionsImpl()));

	}

	public static KandyTestWebDriverActions webDriverActionsInstance() {
		return (KandyTestWebDriverActions) Proxy.newProxyInstance(KandyTestWebDriverActions.class.getClassLoader(),
				new Class[] { KandyTestWebDriverActions.class },
				new ABotActonsHandler(new KandyTestWebDriverActionsImpl()));

	}

	public static CommonDriverAction commanDriverActionsInstance() {
		return (CommonDriverAction) Proxy.newProxyInstance(CommonDriverAction.class.getClassLoader(),
				new Class[] { CommonDriverAction.class }, new ABotActonsHandler(new CommonDriverActionImpl()));

	}
}
