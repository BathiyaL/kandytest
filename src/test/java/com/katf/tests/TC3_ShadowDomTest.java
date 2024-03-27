package com.katf.tests;

import static com.ktsapi.WebActons.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.ktsapi.CommonActions.*;
import org.testng.annotations.Test;
import com.katf.pageobjects.ShadowDomTestPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;

@TestConfiguration(testDriver = TestDriver.WEB,
		browser = Browsers.CHROME,
		baseUrl = "http://watir.com/examples/shadow_dom.html",
		browserOptions = { "--start-maximized", "--ignore-certificate-errors" })
public class TC3_ShadowDomTest {

	@Test
	public void testMethod() {
		GoTo("http://watir.com/examples/shadow_dom.html");
		ShadowDomTestPage shadowPage = GetWebPage(ShadowDomTestPage.class);
		String actualText = shadowPage.nestedText.getText();
		assertThat(actualText).isEqualTo("nested text");
		pause(5);
	}
}
