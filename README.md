# Kandytest
Support test driver : UI, Mobile

## writing you first script

`@TestConfiguration(
		testDriver = TestDriver.WEB, // Mandatory
		browser=Browsers.CHROME,
		implicitlyWaitTime = 15,
		baseUrl = "https://example.com/"
)
public class TestKandyWebElementActionsWithPageObject {
	
	@BeforeTest
	public void goSite() {	 	 
	      OpenBrowser();	      
	      File path =  new File(baseUrl());
	      GoTo("https://example.com/");
	      print(GetTitle());
	 }
	
	@Test
	public void testMothod() {	}
 
 }
`
