# Kandytest
Support test drivers : UI, Mobile

## Writing Web UI test

```
@TestConfiguration(
 testDriver = TestDriver.WEB,
 browser=Browsers.CHROME,
 implicitlyWaitTime = 15,
 baseUrl = "http://watir.com/examples/shadow_dom.html"
)
public class TestKandyWebElementActionsWithPageObject {
	
@BeforeTest
public void goSite() {	 	 
  OpenBrowser();	      
  GoTo(baseUrl());
  print(GetTitle());
}
	
@Test
public void testMothod() {
  hadowDomTestPage shadowPage = getWebPage(ShadowDomTestPage.class);
  String actualText = shadowPage.nestedText.getText();
  assertThat(actualText).isEqualTo("nested text");
  }
}
```

## Writing mobile test
```
@TestConfiguration(
 testDriver = TestDriver.MOBILE_ANDROID,
 mobileApp = "Demo.apk",
 mobileDeviceName = "Pixel_2_XL_API_33",
)
public class TC50_AndroidMobileTestDemo {

@BeforeTest
public void launchApp() {	
  OpenMobileApp();
}
public void testMothod() {	
  AndroidDemoPage page = getAndroidPage(AndroidDemoPage.class);
  page.name.type("A", "B","C");
  page.name.typeWithLocatorParms("x", "y","z");
  hideKeyboard();
  // more code
}
}

```

## Test result reporting
<img width="1505" alt="Screenshot 2024-03-07 at 00 32 29" src="https://github.com/BathiyaL/kandytest/assets/15939220/92321760-b1f9-48e9-9a95-0a14dffb41a9">

