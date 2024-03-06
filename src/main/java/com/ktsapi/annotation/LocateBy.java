package com.ktsapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface LocateBy {
	
	String[] windowHandles() default {};

	String[] frames() default {};

	String[] targets() default {};
	
	String[] shadowLocators() default {};

}
