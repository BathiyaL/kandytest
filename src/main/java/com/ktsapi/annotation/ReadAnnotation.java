package com.ktsapi.annotation;

import java.lang.annotation.Annotation;
import java.util.*; 

public class ReadAnnotation {

	public static void getTestConfiguration(Class<TestConfiguration> testConfigurationClass) {
		Annotation annotation  = testConfigurationClass.getAnnotation(TestConfiguration.class);
		 Map<String, String> map=new HashMap(); 
		if(annotation instanceof TestConfiguration){
			TestConfiguration myAnnotation = (TestConfiguration) annotation;
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>browser: " + myAnnotation.browser());
		    
		    
		}
	}
}
