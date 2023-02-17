package com.katf.element.test;

import static com.ktsapi.WebActons.GoTo;
import static com.ktsapi.WebActons.OpenBrowser;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.katf.pageobjects.GitHubPage;
import com.ktsapi.annotation.TestConfiguration;
import com.ktsapi.enums.Browsers;
import com.ktsapi.enums.TestDriver;
import com.ktsapi.pagefactory.AvtomatPageFactory;
import com.ktsapi.pageobject.SampleFBPage2;

@TestConfiguration(testDriver = TestDriver.WEB,browser=Browsers.CHROME)
public class StaleElementReferenceException_ABotTest {
	
	
    @Test
    public void testFirst(){    	
    	OpenBrowser();
    	GoTo("http://www.github.com");
    	GitHubPage page = AvtomatPageFactory.getWebPage(GitHubPage.class);
    	//page.searchTextBox.type(null);
    	page.searchTextBox.type("Hello");
    	page.searchTextBox.sendKeys(Keys.ENTER);
    	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		page.searchTextBox.clear();
		//Search_Box.sendKeys("Hello");
		//Search_Box.sendKeys(Keys.ENTER);
	}
}
