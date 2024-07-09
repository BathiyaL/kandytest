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

    @Test(priority=0,description = "TC-1")
    public void testMethod1(){
    	print("#### This is testMethod1 : " + baseUrl());
    	pause(5);
    }

    @Test(priority=1,description = "TC-2")
    public void testMethod2(){
    	print("#### This is testMethod2 : " + baseUrl());
    	//System.out.println("#### This is testMethod2 : ");
    	pause(2);
    }
    
    @Test(priority=2,description = "TC-3")
    public void testMethod3(){
    	print("#### This is testMethod3 : " + baseUrl());
    	pause(1);
    }
    
    @Test(priority=2,description = "TC-4")
    public void testMethod4(){
    	print("#### This is testMethod4 : " + baseUrl());
    }
}
