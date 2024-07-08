package com.katf.poc;

import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.TestDriver;
import static com.ktsapi.CommonActions.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

@TestConfiguration(
		testDriver = TestDriver.API,
		baseUrl = "https://petstore.swagger.io"
)
public class TC601_MultipleTestsInClass {

	@BeforeTest
    public void setup(){
		System.out.println("#### This is Before Test1 : " + baseUrl());
    }

    @Test(priority=0)
    public void testMethod1(){
    	print("#### This is testMethod1 : " + baseUrl());
    }

    @Test(priority=1)
    public void testMethod2(){
    	print("#### This is testMethod2 : " + baseUrl());
    	//System.out.println("#### This is testMethod2 : ");
    }
    
    @Test(priority=2)
    public void testMethod3(){
    	print("#### This is testMethod3 : " + baseUrl());
    }
}
