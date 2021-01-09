package com.ktsapi.core;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktsapi.WebActons;

public class HelloWorld {
  @Test
  public void goToUrl() {
	  WebActons.OpenBrowser();
	  WebActons.GoTo("https://github.com/BathiyaL/kandytest");
	  
      Gson gson = new GsonBuilder().disableHtmlEscaping().create();
      String json = gson.toJson(TestConfig.getTestActionsList());
      System.out.println(json);
  }
}
