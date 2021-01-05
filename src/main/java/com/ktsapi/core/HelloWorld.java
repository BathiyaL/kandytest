package com.ktsapi.core;

import org.testng.annotations.Test;

import com.ktsapi.WebActons;

public class HelloWorld {
  @Test
  public void goToUrl() {
	  WebActons.GoTo("https://github.com/BathiyaL/kandytest");
  }
}
